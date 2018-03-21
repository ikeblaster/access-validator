package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Arrays;
import java.util.List;

/**
 * @author ike
 */
public enum YesNoType {
    _ANY(""), YES("Ano"), NO("Ne");

    private String label;

    YesNoType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static List<YesNoType> getChoices() {
        return Arrays.asList(values());
    }
}
