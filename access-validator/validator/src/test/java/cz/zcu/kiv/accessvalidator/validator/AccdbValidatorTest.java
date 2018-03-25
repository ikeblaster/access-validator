package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ike
 */
class AccdbValidatorTest extends BaseTestClass {

    @Test
    void validate_FalseRule_False() throws IOException, URISyntaxException {
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), this.getMockedRule(false));
        assertFalse(accdbValidator.validate());
    }

    @Test
    void validate_TrueRule_True() throws IOException, URISyntaxException {
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), this.getMockedRule(true));
        assertTrue(accdbValidator.validate());
    }

    @Test
    void validate_GroupWithTrueRules_True() throws IOException, URISyntaxException {
        GroupRule groupRule = new GroupRule();
        groupRule.getRules().add(this.getMockedRule(true));
        groupRule.getRules().add(this.getMockedRule(true));
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), groupRule);

        assertTrue(accdbValidator.validate());
    }

    @Test
    void validate_GroupWithFalseRules_False() throws IOException, URISyntaxException {
        GroupRule groupRule = new GroupRule();
        groupRule.getRules().add(this.getMockedRule(false));
        groupRule.getRules().add(this.getMockedRule(false));
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), groupRule);

        assertFalse(accdbValidator.validate());
    }

    @Test
    void validate_GroupWithMixedRules_False() throws IOException, URISyntaxException {
        GroupRule groupRule = new GroupRule();
        groupRule.getRules().add(this.getMockedRule(false));
        groupRule.getRules().add(this.getMockedRule(true));
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), groupRule);

        assertFalse(accdbValidator.validate());
    }

    @Test
    void getFailedRules_GroupWithMixedRules_ContainsFalseRule() throws IOException, URISyntaxException {
        Rule trueRule = this.getMockedRule(true);
        Rule falseRule = this.getMockedRule(false);

        GroupRule groupRule = new GroupRule();
        groupRule.getRules().add(trueRule);
        groupRule.getRules().add(falseRule);
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), groupRule);

        accdbValidator.validate();

        assertTrue(accdbValidator.getFailedRules().contains(falseRule));
    }

    @Test
    void getFailedRules_NotGroupRule_IsEmpty() throws IOException, URISyntaxException {
        AccdbValidator accdbValidator = new AccdbValidator(this.getTestDBFile(), this.getMockedRule(false));
        accdbValidator.validate();

        assertTrue(accdbValidator.getFailedRules().isEmpty());
    }



    private Rule getMockedRule(boolean value) {
        Rule rule = Mockito.mock(Rule.class);
        Mockito.when(rule.check(Mockito.any(Accdb.class))).thenReturn(value);
        return rule;
    }
}