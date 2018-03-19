package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ike
 */
class Relation1NExistsRuleTest extends BaseTestClass {

    Relation1NExistsRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new Relation1NExistsRule();
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
    void toString__NotNullNorEmpty() {
        assertNotNull(this.rule.toString());
        assertFalse(this.rule.toString().isEmpty());
    }

}