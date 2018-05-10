package cz.zcu.kiv.accessvalidator.validator.rules;

import com.healthmarketscience.jackcess.Relationship;
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
class CountRelations11RuleTest extends BaseRulesTestClass {

    private CountRelations11Rule rule;

    @BeforeEach
    void setUp() {
        this.rule = new CountRelations11Rule();
    }

    @Test
    void newInstance__CtorWithoutParametersExists() {
        this.rule.newInstance();
    }

    @Test
    void check_EmptyDB_False() {
        Accdb mockAccdb = this.getMockedAccdb();

        assertFalse(this.rule.check(mockAccdb));
    }

    @Test
    void check_SingleRelation_True() {
        Accdb mockAccdb = this.getMockedAccdb();

        Mockito.when(mockAccdb.getRelationRepository().getRelations())
                .thenReturn(new HashSet<>(Collections.singleton(Mockito.mock(Relationship.class))));

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