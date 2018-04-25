package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Collection;

/**
 * Property with predefined choices.
 *
 * @author ike
 */
public class ChoiceProperty<T> extends Property<T> {

    /**
     * All possible values.
     */
    private final Collection<T> choices;

    /**
     * Property with predefined choices.
     *
     * @param id Internal ID.
     * @param type Choices data type.
     * @param value Default value. Must be part of {@code choices}
     * @param choices All possible choices (values).
     * @param name Name of property.
     * @param description Description of property.
     * @param category Category of property
     */
    public ChoiceProperty(String id, Class<T> type, T value, Collection<T> choices, String name, String description, String category) {
        super(id, type, value, name, description, category);
        this.choices = choices;
    }

    /**
     * Gets collection of all possible choices (values).
     *
     * @return Collection of acceptable values.
     */
    public final Collection<T> getChoices() {
        return this.choices;
    }

    /**
     * Sets new value.
     *
     * @throws RuntimeException for unknown choices.
     * @param value New value
     */
    @Override
    public void setValue(T value) {
        if(!this.choices.contains(value)) {
            throw new RuntimeException("Unknown choice");
        }

        super.setValue(value);
    }

}
