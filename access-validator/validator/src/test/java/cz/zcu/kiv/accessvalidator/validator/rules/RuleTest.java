package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vojtech Kinkor
 */
class RuleTest extends BaseRulesTestClass {

    private _RuleWithoutDefaultConstructor rule;

    @BeforeEach
    void setUp() {
        this.rule = new _RuleWithoutDefaultConstructor(0);
    }

    @Test
    void newInstance_BrokenRuleWithoutDefaultConstructor_RuntimeExceptionThrown() {
        assertThrows(RuntimeException.class, this.rule::newInstance);
    }

    @Test
    void getProperty_nullAndEmpty_Null() {
        assertNull(this.rule.getProperty(null));
        assertNull(this.rule.getProperty(""));
    }


}
