package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Represents comparison operator, typically used for comparing two integers.
 *
 * @author Vojtech Kinkor
 */
public enum ComparisonOperator {
    GTE("≥"), EQ("="), LTE("≤");

    /**
     * Label of operator.
     */
    private String label;

    /**
     * Represents comparison operator, typically used for comparing two integers.
     *
     * @param label Label of operator.
     */
    ComparisonOperator(String label) {
        this.label = label;
    }

    /**
     * Gets label of operator.
     * @return Label of operator.
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Gets all operators as collection.
     * @return Collection of operators.
     */
    public static List<ComparisonOperator> getChoices() {
        return Arrays.asList(values());
    }

    /**
     * Compares arguments using this operator.
     * @param a First value.
     * @param b Second value.
     * @return Result of comparison.
     */
    public boolean compare(int a, int b) {
        if(this == ComparisonOperator.GTE) return a >= b;
        if(this == ComparisonOperator.LTE) return a <= b;
        return a == b;
    }
}
