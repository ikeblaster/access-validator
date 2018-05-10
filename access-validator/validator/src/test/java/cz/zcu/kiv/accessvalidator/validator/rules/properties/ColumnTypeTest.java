package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.DataType;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Vojtech Kinkor
 */
class ColumnTypeTest extends BaseTestClass {

    private Column column;

    @BeforeEach
    void setUp() {
        this.column = Mockito.mock(Column.class);
        Mockito.when(this.column.getType()).thenReturn(DataType.UNKNOWN_0D);
    }

    @Test
    void getChoices__ContainsValues() {
        assertEquals(Arrays.asList(ColumnType.values()), ColumnType.getChoices());
    }

    @Test
    void compare_ColumnTypeANY_True() {
        assertTrue(ColumnType._ANY.compare(this.column));
    }

    @ParameterizedTest
    @EnumSource(ColumnType.class)
    void compare_UnknownDatatype_False(ColumnType columnType) {
        if(columnType != ColumnType._ANY) {
            assertFalse(columnType.compare(this.column));
        }
    }
}