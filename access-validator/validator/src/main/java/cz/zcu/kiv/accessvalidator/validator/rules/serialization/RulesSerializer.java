package cz.zcu.kiv.accessvalidator.validator.rules.serialization;

import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import javax.xml.stream.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Provides interface for serializing and deserializing rules (see {@code Rule}).
 * Uses XML format.
 *
 * @author ike
 */
public class RulesSerializer {

    /**
     * This prefix + {@code simpleName} of rules class must result in fully qualified name of that class.
     */
    private static final String PACKAGE_PREFIX = Rule.class.getPackage().getName() + ".";


    //region Serializer
    /**
     * Serializes a rule (possibly tree of rules). Uses XML format.
     *
     * @param root Root rule.
     * @param outputStream The stream to write to.
     * @throws XMLStreamException
     */
    public void serialize(Rule root, OutputStream outputStream) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream);

        writer.writeStartDocument();
        this.writeRule(root, writer);
        writer.writeEndDocument();

        writer.flush();
        writer.close();
    }

    /**
     * Writes a rule together with its properties into stream.
     * Recursively called for {@code GroupRule}.
     *
     * @param rule Rule to be written.
     * @param writer The stream to write to.
     * @throws XMLStreamException
     */
    private void writeRule(Rule rule, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(rule.getClass().getSimpleName());

        // write all properties as attributes
        for(Property<?> property : rule.getProperties()) {
            String value;

            if(property.getValue() instanceof Enum) {
                value = ((Enum<?>) property.getValue()).name();
            }
            else {
                value = property.getValue().toString();
            }

            writer.writeAttribute(property.getId(), value);
        }

        // for group rules recursively write all children
        if(rule instanceof GroupRule) {
            GroupRule groupRule = (GroupRule) rule;
            for(Rule child : groupRule.getRules()) {
                this.writeRule(child, writer);
            }
        }

        writer.writeEndElement();
    }
    //endregion


    //region Deserializer
    /**
     * Deserializes a rule (possibly tree of rules) from input stream. Uses XML format.
     *
     * @param inputStream The stream to read from.
     * @return Deserialized rule or {@code null} when no rule or unknown rule found.
     * @throws XMLStreamException
     */
    public Rule deserialize(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        Rule rule = null;

        if(reader.hasNext()){
            reader.next();
            if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
                rule = this.readRule(reader);
            }
        }

        reader.close();
        return rule;
    }

    /**
     * Reads a rule together with its properties from stream.
     * When unknown rule or property found, error message is written to {@code System.err}.
     *
     * @param reader The stream to read from.
     * @return Deserialized rule or {@code null} when no rule or unknown rule found.
     * @throws XMLStreamException
     */
    private Rule readRule(XMLStreamReader reader) throws XMLStreamException {
        Rule rule = null;
        String ruleClassName = PACKAGE_PREFIX + reader.getLocalName();

        try {
            Object obj = Class.forName(ruleClassName).getConstructor().newInstance();
            rule = Rule.class.cast(obj);

            // parse properties (attributes)
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                String propertyName = reader.getAttributeLocalName(i);
                Property<?> property = rule.getProperty(propertyName);

                if (property == null) {
                    System.err.println("Deserialization: Unknown property '" + propertyName + "', skipping");
                    continue;
                }

                Class<?> type = property.getType();
                String value = reader.getAttributeValue(i);


                // parse enums (find exact choice by string name)
                if (property instanceof ChoiceProperty && type.isEnum()) {
                    @SuppressWarnings("unchecked")
                    ChoiceProperty<Enum<?>> enumChoiceProperty = (ChoiceProperty<Enum<?>>) property;

                    Collection<Enum<?>> choices = enumChoiceProperty.getChoices();

                    for (Enum<?> choice : choices) {
                        if (choice.name().equals(value)) {
                            property.setRawValue(choice);
                            break;
                        }
                    }
                } else if (type == Integer.class) {
                    property.setRawValue(Integer.valueOf(value));
                } else if (type == String.class) {
                    property.setRawValue(String.valueOf(value));
                } else if (type == Boolean.class) {
                    property.setRawValue(Boolean.valueOf(value));
                } else {
                    System.err.println("Deserialization: Type of property '" + propertyName + "' is not supported by serializer, unable to set value");
                }
            }

            // parse children
            while (reader.hasNext()) {
                reader.next();
                if (reader.getEventType() == XMLStreamReader.START_ELEMENT && rule instanceof GroupRule) {

                    Rule child = this.readRule(reader);
                    if(child != null) {
                        ((GroupRule) rule).getRules().add(child);
                    }

                } else if (reader.getEventType() == XMLStreamReader.END_ELEMENT) {
                    break;
                }
            }
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            System.err.println("Deserialization: Rule '" + ruleClassName + "' not found or doesn't have default constructor, skipping");
        }

        return rule;
    }
    //endregion

}
