package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.BaseRulesTestClass;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ike
 */
class ComplexRuleTest extends BaseRulesTestClass {

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
        Accdb mockAccdb = this.getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.singleton("tbl"));

        assertTrue(this.rule.check(mockAccdb)); // true since only active rule is propTablesCount >= 1
    }

    @Test
    void check_ReturnConditionWhenNoTablesFound_False() {
        Accdb mockAccdb = this.getMockedAccdb();
        Mockito.when(mockAccdb.getTableRepository().getTables()).thenReturn(Collections.emptySet());

        this.rule.getProperty("tables_byname").setRawValue("tbl");

        assertFalse(this.rule.check(mockAccdb)); // false since only active rule is propTablesCount >= 1
    }

    @Test
    void onChange_PropertyChanged_ListenerTriggered() {
        InvalidationListener listener = Mockito.mock(InvalidationListener.class);
        this.rule.onChange(listener);

        this.rule.getProperty("tables_byname").setRawValue("newValue");

        Mockito.verify(listener, Mockito.times(1)).invalidated(Mockito.any(Observable.class));
    }

    // TODO: test na reálné DB


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