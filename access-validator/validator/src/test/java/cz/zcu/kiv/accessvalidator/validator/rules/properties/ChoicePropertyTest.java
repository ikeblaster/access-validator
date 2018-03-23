package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ike
 */
class ChoicePropertyTest extends BaseTestClass {

    private enum TestEnum {
        OPT1, OPT2
    }

    private final List<String> stringChoices = Arrays.asList("opt1", "opt2");
    private final List<TestEnum> enumChoices = Arrays.asList(TestEnum.values());

    private ChoiceProperty<String> stringProperty;
    private ChoiceProperty<TestEnum> enumProperty;

    @BeforeEach
    void setUp() {
        this.stringProperty = new ChoiceProperty<>("id", String.class, "opt1", this.stringChoices, "name", "desc", "cat");
        this.enumProperty = new ChoiceProperty<>("id", TestEnum.class, TestEnum.OPT1, this.enumChoices, "name", "desc", "cat");
    }


    @Test
    void getChoices_StringChoices_EqualsToCtorPar() {
        assertEquals(this.stringChoices, this.stringProperty.getChoices());
    }

    @Test
    void getValue_StringChoice_EqualsToCtorPar() {
        assertEquals("opt1", this.stringProperty.getValue());
    }

    @Test
    void setValue_ValidStringChoice_GetValueReturnsTheChoice() {
        this.stringProperty.setValue("opt2");
        assertEquals("opt2", this.stringProperty.getValue());
    }

    @Test
    void setValue_UnknownStringChoice_RuntimeExceptionThrown() {
        assertThrows(RuntimeException.class, () -> this.stringProperty.setValue("unknown"));
    }

    @Test
    void setValue_UnknownStringChoice_GetValueRemainsUnchanged() {
        String def = this.stringProperty.getValue();
        assertThrows(RuntimeException.class, () -> this.stringProperty.setValue("unknown"));
        assertEquals(def, this.stringProperty.getValue());
    }


    @Test
    void getChoices_EnumChoices_EqualsToCtorPar() {
        assertEquals(this.enumChoices, this.enumProperty.getChoices());
    }

    @Test
    void getValue_EnumChoice_EqualsToCtorPar() {
        assertEquals(TestEnum.OPT1, this.enumProperty.getValue());
    }

    @Test
    void setValue_ValidEnumChoice_GetValueReturnsTheChoice() {
        this.enumProperty.setValue(TestEnum.OPT2);
        assertEquals(TestEnum.OPT2, this.enumProperty.getValue());
    }

    @Test
    void setValue_NullEnumChoice_RuntimeExceptionThrown() {
        assertThrows(RuntimeException.class, () -> this.enumProperty.setValue(null));
    }

    @Test
    void setValue_NullEnumChoice_GetValueRemainsUnchanged() {
        TestEnum def = this.enumProperty.getValue();
        assertThrows(RuntimeException.class, () -> this.enumProperty.setValue(null));
        assertEquals(def, this.enumProperty.getValue());
    }

}