package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.query.Query;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class QueryTypeTest extends BaseTestClass {

    private Query query;

    @BeforeEach
    void setUp() {
        this.query = Mockito.mock(Query.class);
        Mockito.when(this.query.getType()).thenReturn(Query.Type.UNKNOWN);
    }

    @Test
    void getChoices__ContainsValues() {
        assertEquals(Arrays.asList(ColumnType.values()), ColumnType.getChoices());
    }

    @Test
    void compare_ColumnTypeANY_True() {
        assertTrue(QueryType._ANY.compare(this.query));
    }

    @ParameterizedTest
    @EnumSource(QueryType.class)
    void compare_UnknownDatatype_False(QueryType queryType) {
        if(queryType != QueryType._ANY) {
            assertFalse(queryType.compare(this.query));
        }
    }
}