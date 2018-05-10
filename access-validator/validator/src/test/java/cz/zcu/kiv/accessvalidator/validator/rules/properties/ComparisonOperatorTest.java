package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vojtech Kinkor
 */
class ComparisonOperatorTest extends BaseTestClass {

    @Test
    void toString_EqOperator_NotNullNorEmpty() {
        assertNotNull(ComparisonOperator.EQ.toString());
        assertFalse(ComparisonOperator.EQ.toString().isEmpty());
    }

    @Test
    void toString_GteOperator_NotNullNorEmpty() {
        assertNotNull(ComparisonOperator.GTE.toString());
        assertFalse(ComparisonOperator.GTE.toString().isEmpty());
    }

    @Test
    void toString_LteOperator_NotNullNorEmpty() {
        assertNotNull(ComparisonOperator.LTE.toString());
        assertFalse(ComparisonOperator.LTE.toString().isEmpty());
    }

    @Test
    void toString_AllOperators_NotEquals() {
        assertNotEquals(ComparisonOperator.EQ.toString(), ComparisonOperator.LTE.toString());
        assertNotEquals(ComparisonOperator.EQ.toString(), ComparisonOperator.GTE.toString());
        assertNotEquals(ComparisonOperator.LTE.toString(), ComparisonOperator.GTE.toString());
    }

    @Test
    void compare_EqOperatorSameValues_True() {
        assertTrue(ComparisonOperator.EQ.compare(0, 0));
        assertTrue(ComparisonOperator.EQ.compare(1, 1));
        assertTrue(ComparisonOperator.EQ.compare(-1, -1));
        assertTrue(ComparisonOperator.EQ.compare(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    void compare_EqOperatorDifferentValues_False() {
        assertFalse(ComparisonOperator.EQ.compare(Integer.MAX_VALUE, 0));
        assertFalse(ComparisonOperator.EQ.compare(0, Integer.MAX_VALUE));
        assertFalse(ComparisonOperator.EQ.compare(Integer.MAX_VALUE, -Integer.MAX_VALUE));
        assertFalse(ComparisonOperator.EQ.compare(1, -1));
    }

    @Test
    void compare_GteOperator_True() {
        assertTrue(ComparisonOperator.GTE.compare(1, 0));
        assertTrue(ComparisonOperator.GTE.compare(0, 0));
        assertTrue(ComparisonOperator.GTE.compare(0, -1));
        assertTrue(ComparisonOperator.GTE.compare(Integer.MAX_VALUE, 0));
    }

    @Test
    void compare_GteOperator_False() {
        assertFalse(ComparisonOperator.GTE.compare(0, 1));
        assertFalse(ComparisonOperator.GTE.compare(-1, 0));
        assertFalse(ComparisonOperator.GTE.compare(0, Integer.MAX_VALUE));
    }

    @Test
    void compare_LteOperator_True() {
        assertTrue(ComparisonOperator.LTE.compare(0, 1));
        assertTrue(ComparisonOperator.LTE.compare(0, 0));
        assertTrue(ComparisonOperator.LTE.compare(-1, 0));
        assertTrue(ComparisonOperator.LTE.compare(0, Integer.MAX_VALUE));
    }

    @Test
    void compare_LteOperator_False() {
        assertFalse(ComparisonOperator.LTE.compare(1, 0));
        assertFalse(ComparisonOperator.LTE.compare(0, -1));
        assertFalse(ComparisonOperator.LTE.compare(Integer.MAX_VALUE, 0));
    }

    @Test
    void getChoices_ContainsEqGteLte_True() {
        assertTrue(ComparisonOperator.getChoices().contains(ComparisonOperator.EQ));
        assertTrue(ComparisonOperator.getChoices().contains(ComparisonOperator.GTE));
        assertTrue(ComparisonOperator.getChoices().contains(ComparisonOperator.LTE));
    }

    @Test
    void getChoices__HasThreeItems() {
        assertTrue(ComparisonOperator.getChoices().size() == 3);
    }

}