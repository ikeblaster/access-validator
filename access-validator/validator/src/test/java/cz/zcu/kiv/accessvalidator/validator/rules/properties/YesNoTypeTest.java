package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Vojtech Kinkor
 */
class YesNoTypeTest extends BaseTestClass {

    @Test
    void getChoices__ContainsValues() {
        assertEquals(Arrays.asList(ColumnType.values()), ColumnType.getChoices());
    }

    @Test
    void toString_YES_NotNullNorEmpty() {
        assertNotNull(YesNoType.YES.toString());
        assertFalse(YesNoType.YES.toString().isEmpty());
    }

    @Test
    void toString_NO_NotNullNorEmpty() {
        assertNotNull(YesNoType.NO.toString());
        assertFalse(YesNoType.NO.toString().isEmpty());
    }

    @Test
    void toString_ANY_NotNull() {
        assertNotNull(YesNoType._ANY.toString());
    }

}