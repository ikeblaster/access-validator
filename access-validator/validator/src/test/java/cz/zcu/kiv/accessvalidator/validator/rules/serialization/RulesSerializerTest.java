package cz.zcu.kiv.accessvalidator.validator.rules.serialization;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import cz.zcu.kiv.accessvalidator.validator.rules.ComplexRule;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules._RuleWithoutDefaultConstructor;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.xml.stream.XMLStreamException;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Vojtech Kinkor
 */
class RulesSerializerTest extends BaseTestClass {

    private RulesSerializer serializer;
    private GroupRule reference;

    @BeforeEach
    void setUp() {
        GroupRule root = new GroupRule();
        root.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        ComplexRule rule1 = new ComplexRule();
        rule1.getProperty("tables_count_op").setRawValue(ComparisonOperator.LTE);
        rule1.getProperty("tables_count").setRawValue(987);
        rule1.getProperty("tables_byname").setRawValue("someName");

        GroupRule rule2 = new GroupRule();
        rule2.getRules().add(new ComplexRule());
        rule2.getRules().add(new ComplexRule());
        rule2.getRules().add(new ComplexRule());

        root.getRules().add(rule1);
        root.getRules().add(rule2);

        this.reference = root;
        this.serializer = new RulesSerializer();
    }

    @Test
    void serialize_ManyRules_OutputStreamUsed() throws XMLStreamException, IOException {
        OutputStream stream = Mockito.mock(OutputStream.class);
        this.serializer.serialize(this.reference, stream);

        Mockito.verify(stream, Mockito.atLeastOnce()).write(Mockito.anyInt());
    }

    @Test
    void deserialize_ManyRules_EqualsToReference() throws XMLStreamException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.serializer.serialize(this.reference, outputStream);

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Rule rule = this.serializer.deserialize(inputStream);

        assertTrue(rule instanceof GroupRule);
        assertEquals(this.reference, rule); // also checks equals/hashCode methods
    }

    @Test
    void deserialize_BrokenRuleWithUnsupportedAndUnknownProperties_ErrorMessagesPrintedToSystemErr() throws XMLStreamException {
        Rule ref = new _RuleWithUnsupportedProperty();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.serializer.serialize(ref, outputStream);

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        this.serializer.deserialize(inputStream);

        assertTrue(errContent.toString().contains("id_unknown"));
        assertTrue(errContent.toString().contains("id_unsupported"));
    }

    @Test
    void deserialize_BrokenRuleWithoutDefaultConstructor_ExceptionThrown() throws XMLStreamException {
        Rule ref = new _RuleWithoutDefaultConstructor(0);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.serializer.serialize(ref, outputStream);

        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        assertThrows(RuntimeException.class, () -> this.serializer.deserialize(inputStream));
    }


    /**
     * Just for mocking serializer.
     *
     * This class is serialized, but when deserializing,
     * public class is used instead => differences in properties.
     */
    class _RuleWithUnsupportedProperty extends cz.zcu.kiv.accessvalidator.validator.rules._RuleWithUnsupportedProperty {
        _RuleWithUnsupportedProperty() {
            super();
            this.properties.add(new Property<>("id_unknown", Boolean.class, true, "", "", ""));
        }
    }


}