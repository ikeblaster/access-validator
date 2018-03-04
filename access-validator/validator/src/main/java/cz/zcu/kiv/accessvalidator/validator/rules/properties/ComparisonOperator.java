package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Arrays;
import java.util.List;

/**
 * @author ike
 */

public enum ComparisonOperator {
    GTE("=>"), EQ("="), LTE("<=");

    private String label;

    ComparisonOperator(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public boolean compare(int a, int b) {
        if(this == ComparisonOperator.GTE) return a >= b;
        if(this == ComparisonOperator.LTE) return a <= b;
        return a == b;
    }

    public static List<ComparisonOperator> asList() {
        return Arrays.asList(values());
    }

}