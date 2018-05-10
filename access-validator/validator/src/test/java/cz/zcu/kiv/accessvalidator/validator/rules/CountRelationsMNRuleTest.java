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
class CountRelationsMNRuleTest extends BaseRulesTestClass {

    private CountRelationsMNRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new CountRelationsMNRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_EmptyDB_False() {
        assertFalse(this.rule.check(this.getMockedAccdb()));
    }

    @Test
    void check_SingleTable_True() {
        Accdb mockAccdb = this.getMockedAccdb();

        Mockito.when(mockAccdb.getTableRepository().getTables())
                .thenReturn(new HashSet<>(Collections.singleton("tbl")));

        assertTrue(this.rule.check(mockAccdb));
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