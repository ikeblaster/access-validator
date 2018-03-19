package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class ComplexRuleTest extends BaseTestClass {

    private ComplexRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new ComplexRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_ReturnConditionWhenTablesFound_True() {
        Accdb mockAccdb = getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.singleton("tbl"));

        assertTrue(this.rule.check(mockAccdb)); // true since only active rule is propTablesCount >= 1
    }

    @Test
    void check_ReturnConditionWhenNoTablesFound_False() {
        Accdb mockAccdb = getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.emptySet());

        assertFalse(this.rule.check(mockAccdb)); // false since only active rule is propTablesCount >= 1
    }

    // TODO: test na reálné DB


    @Test
    void toString__NotNullNorEmpty() {
        assertNotNull(this.rule.toString());
        assertFalse(this.rule.toString().isEmpty());
    }

}