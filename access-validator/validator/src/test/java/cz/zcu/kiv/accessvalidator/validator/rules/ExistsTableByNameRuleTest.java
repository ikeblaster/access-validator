package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class ExistsTableByNameRuleTest extends BaseRulesTestClass {

    private ExistsTableByNameRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new ExistsTableByNameRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_ReturnConditionWhenTablesFound_True() {
        Accdb mockAccdb = this.getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.singleton("tbl"));

        assertTrue(this.rule.check(mockAccdb)); // true since only active rule is propTablesCount >= 1
    }

    @Test
    void check_ReturnConditionWhenNoTablesFound_False() {
        Accdb mockAccdb = this.getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.emptySet());

        assertFalse(this.rule.check(mockAccdb)); // false since only active rule is propTablesCount >= 1
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