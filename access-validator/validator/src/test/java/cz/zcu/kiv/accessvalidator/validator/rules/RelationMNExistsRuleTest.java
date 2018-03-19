package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class RelationMNExistsRuleTest extends BaseTestClass {

    RelationMNExistsRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new RelationMNExistsRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_ByDefault_False() {
        assertFalse(this.rule.check(getMockedAccdb()));
    }

    @Test
    void check_ByDefault_True() {
        Accdb mockAccdb = getMockedAccdb();

        Mockito.when(mockAccdb.getTableRepository().getTables())
                .thenReturn(new HashSet<>(Collections.singleton("tbl")));

        assertTrue(this.rule.check(mockAccdb));
    }

    @Test
    void toString__NotNullNorEmpty() {
        assertNotNull(this.rule.toString());
        assertFalse(this.rule.toString().isEmpty());
    }

}