package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vojtech Kinkor
 */
class AllTablesHaveRowsRuleTest extends BaseRulesTestClass {

    private AllTablesHaveRowsRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new AllTablesHaveRowsRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_ReturnConditionWhenTableFound_True() {
        Accdb mockAccdb = this.getMockedAccdb();

        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.singleton("tbl"));

        assertTrue(this.rule.check(mockAccdb)); // true since even after filtering tables there is still the same amount of tables
    }

    @Test
    void check_ReturnConditionWhenLessTablesFound_False() {
        Accdb mockAccdb = this.getMockedAccdb();

        HashSet<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");

        // on every getTables call delete one item and return the rest
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(set);
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenAnswer(invocationOnMock -> {
            set.remove(set.iterator().next());
            return set;
        });

        assertFalse(this.rule.check(mockAccdb)); // false since there was 3 tables in total, but only 2 tables matched the rows count rule
    }

    @Test
    void getGenericLabel__NotNullNorEmpty() {
        assertNotNull(this.rule.getGenericLabel());
        assertFalse(this.rule.getGenericLabel().isEmpty());
    }

    @Test
    void toString__NotNullNorEmpty() {
        assertNotNull(this.rule.toString());
        assertFalse(this.rule.toString().isEmpty());
    }

}