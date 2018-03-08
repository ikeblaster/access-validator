package cz.zcu.kiv.accessvalidator.validator.rules.serialization;

import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import javax.xml.stream.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @author ike
 */
public class RulesSerializer {

    private final String packagePrefix = Rule.class.getPackage().getName() + ".";

    //region Serializer
    public void serialize(Rule root, OutputStream outputStream) throws XMLStreamException, IOException {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(outputStream);

        writer.writeStartDocument();
        this.writeRule(root, writer);
        writer.writeEndDocument();

        writer.flush();
        writer.close();
    }

    private void writeRule(Rule rule, XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(rule.getClass().getSimpleName());

        for(Property property : rule.getProperties()) {
            String value = property.getValue().toString();

            if(property.getValue() instanceof Enum) {
                value = ((Enum) property.getValue()).name();
            }

            writer.writeAttribute(property.getId(), value);
        }

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
    public Rule deserialize(InputStream inputStream) throws XMLStreamException, IOException {
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

    @SuppressWarnings("unchecked")
    private Rule readRule(XMLStreamReader reader) throws XMLStreamException {
        Rule rule = null;

        try {
            Object obj = Class.forName(this.packagePrefix + reader.getLocalName()).getConstructor().newInstance();
            rule = Rule.class.cast(obj);

            // parse properties (attributes)
            for (int i = 0; i < reader.getAttributeCount(); i++) {
                String propertyName = reader.getAttributeLocalName(i);
                Property property = rule.getProperty(propertyName);

                if (property == null) {
                    System.err.println("Deserialization: Unknown property '" + propertyName + "', skipping");
                    continue;
                }

                Class<?> type = property.getType();
                String value = reader.getAttributeValue(i);


                // parse enums (find exact choice by string name)
                if (property instanceof ChoiceProperty && type.isEnum()) {
                    Collection<Enum> choices = ((ChoiceProperty) property).getChoices();

                    for (Enum choice : choices) {
                        if (choice.name().equals(value)) {
                            property.setValue(choice);
                            break;
                        }
                    }
                } else if (type == Integer.class) {
                    property.setValue(Integer.valueOf(value));
                } else if (type == String.class) {
                    property.setValue(value);
                } else if (type == Boolean.class) {
                    property.setValue(Boolean.valueOf(value));
                } else {
                    System.err.println("Deserialization: Unknown property type, unable to set value");
                }
            }

            // parse children
            while (reader.hasNext()) {
                reader.next();
                if (reader.getEventType() == XMLStreamReader.START_ELEMENT && rule instanceof GroupRule) {

                    ((GroupRule) rule).getRules().add(this.readRule(reader));

                } else if (reader.getEventType() == XMLStreamReader.END_ELEMENT) {
                    break;
                }
            }
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return rule;
    }
    //endregion

}
