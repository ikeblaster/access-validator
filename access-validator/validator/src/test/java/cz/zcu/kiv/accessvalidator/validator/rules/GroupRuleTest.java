package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class GroupRuleTest extends BaseRulesTestClass {

    private GroupRule rule;

    @Mock
    private Accdb mockAccdb;


    @BeforeEach
    void setUp() {
        this.rule = new GroupRule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void getRules_ByDefault_IsEmpty() {
        assertTrue(this.rule.getRules().isEmpty());
    }

    @Test
    void getRules_AddedThreeRules_HasThreeItems() {
        this.rule.getRules().add(this.getMockedRule(true));
        this.rule.getRules().add(this.getMockedRule(true));
        this.rule.getRules().add(this.getMockedRule(false));

        assertTrue(this.rule.getRules().size() == 3);
    }

    @Test
    void getProperties_ByDefault_HasOneItem() {
        assertTrue(this.rule.getProperties().size() == 1);
    }

    @Test
    void getProperty_mode_IsInstanceOfChoicePropertyGroupRuleMode() {
        assertTrue(this.rule.getProperty("mode") instanceof ChoiceProperty);
    }

    @Test
    void getProperty_modeByDefault_IsAND() {
        assertEquals(this.rule.getProperty("mode").getValue(), GroupRule.Mode.AND);
    }

    @Test
    void getProperty_modeSetToAND_IsAND() {
        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.AND);
        assertEquals(this.rule.getProperty("mode").getValue(), GroupRule.Mode.AND);
    }

    @Test
    void getProperty_modeSetToOR_IsOR() {
        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);
        assertEquals(this.rule.getProperty("mode").getValue(), GroupRule.Mode.OR);
    }

    @Test
    void check_EmptyRules_True() {
        assertTrue(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_TrueANDFalseRules_False() {
        this.rule.getRules().add(this.getMockedRule(true));
        this.rule.getRules().add(this.getMockedRule(false));

        assertFalse(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_FalseANDTrueRules_False() {
        this.rule.getRules().add(this.getMockedRule(false));
        this.rule.getRules().add(this.getMockedRule(true));

        assertFalse(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_TrueRulesANDMode_True() {
        this.rule.getRules().add(this.getMockedRule(true));
        assertTrue(this.rule.check(this.mockAccdb));

        this.rule.getRules().add(this.getMockedRule(true));
        assertTrue(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_FalseRulesORMode_False() {
        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        this.rule.getRules().add(this.getMockedRule(false));
        assertFalse(this.rule.check(this.mockAccdb));

        this.rule.getRules().add(this.getMockedRule(false));
        assertFalse(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_TrueORFalseRules_True() {
        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        this.rule.getRules().add(this.getMockedRule(true));
        this.rule.getRules().add(this.getMockedRule(false));

        assertTrue(this.rule.check(this.mockAccdb));
    }

    @Test
    void check_FalseORTrueRules_True() {
        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        this.rule.getRules().add(this.getMockedRule(false));
        this.rule.getRules().add(this.getMockedRule(true));

        assertTrue(this.rule.check(this.mockAccdb));
    }

    @Test
    void getFailedRules_EmptyRules_Empty() {
        this.rule.check(this.mockAccdb);
        assertTrue(this.rule.getFailedRules().isEmpty());
    }

    @Test
    void getFailedRules_NestedGroupRules_ContainsFailedRule() {
        Rule failed = this.getMockedRule(false);
        GroupRule failedParent = new GroupRule();
        failedParent.getRules().add(failed);

        this.rule.getRules().add(failedParent);

        this.rule.check(this.mockAccdb);
        assertTrue(this.rule.getFailedRules().contains(failed));
        assertTrue(this.rule.getFailedRules().contains(failedParent));
    }

    @Test
    void check_TrueANDFalseRules_ContainsFailedRule() {
        Rule failed = this.getMockedRule(false);

        this.rule.getRules().add(this.getMockedRule(true));
        this.rule.getRules().add(failed);

        this.rule.check(this.mockAccdb);
        assertTrue(this.rule.getFailedRules().contains(failed));
    }

    @Test
    void onChange_ModeChanged_ListenerTriggered() {
        InvalidationListener listener = Mockito.mock(InvalidationListener.class);
        this.rule.onChange(listener);

        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        Mockito.verify(listener, Mockito.times(1)).invalidated(Mockito.any(Observable.class));
    }

    @Test
    void hashCode_TwoInstancesSameMode_Equals() {
        GroupRule rule2 = new GroupRule();

        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.OR);
        rule2.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        assertEquals(this.rule.hashCode(), rule2.hashCode());
    }

    @Test
    void hashCode_TwoInstancesDifferentMode_NotEquals() {
        GroupRule rule2 = new GroupRule();

        this.rule.getProperty("mode").setRawValue(GroupRule.Mode.AND);
        rule2.getProperty("mode").setRawValue(GroupRule.Mode.OR);

        assertNotEquals(this.rule.hashCode(), rule2.hashCode());
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

    private Rule getMockedRule(boolean value) {
        Rule rule = Mockito.mock(Rule.class);
        Mockito.when(rule.check(Mockito.any(Accdb.class))).thenReturn(value);
        return rule;
    }

}
