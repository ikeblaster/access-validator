package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import javafx.beans.InvalidationListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * @author ike
 */
class RuleTest extends BaseTestClass {

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

    @Test
    void onChange_ByDefault_UnsupportedOperationExceptionThrown() {
        InvalidationListener listener = mock(InvalidationListener.class);
        assertThrows(UnsupportedOperationException.class, () -> this.rule.onChange(listener));
    }


}
