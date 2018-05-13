package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ColumnType;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.YesNoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vojtech Kinkor
 */
class CountTablesWithColumnRuleTest extends BaseRulesTestClass {
    private CountTablesWithColumnRule rule;

    @BeforeEach
    void setUp() {
        this.rule = new CountTablesWithColumnRule();
        this.rule.getProperty("column_name").setRawValue("col");
        this.rule.getProperty("column_type").setRawValue(ColumnType.NUMBER);
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
    void getGenericLabel__NotNullNorEmpty() {
        assertNotNull(this.rule.getGenericLabel());
        assertFalse(this.rule.getGenericLabel().isEmpty());
    }

    @Test
    void toString__NotNullNorEmpty() {
        assertNotNull(this.rule.toString());
        assertFalse(this.rule.toString().isEmpty());
    }

    @Test
    void toString_PrimarySpecified_NotEqualToDefault() {
        String def = this.rule.toString();

        this.rule.getProperty("column_primary").setRawValue(YesNoType.YES);

        assertNotEquals(def, this.rule.toString());
    }

}