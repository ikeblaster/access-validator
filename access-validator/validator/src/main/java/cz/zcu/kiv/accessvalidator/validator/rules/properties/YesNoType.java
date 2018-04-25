package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Arrays;
import java.util.List;

/**
 * Represents simple ANY/YES/NO value type.
 *
 * @author ike
 */
public enum YesNoType {
    _ANY(""), YES("Ano"), NO("Ne");

    /**
     * Label of value.
     */
    private String label;

    /**
     * Represents simple ANY/YES/NO value type.
     *
     * @param label Label for value.
     */
    YesNoType(String label) {
        this.label = label;
    }

    /**
     * Gets current value label.
     *
     * @return Label for value.
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Gets all three values as collection.
     *
     * @return Collection of all values.
     */
    public static List<YesNoType> getChoices() {
        return Arrays.asList(values());
    }

}
