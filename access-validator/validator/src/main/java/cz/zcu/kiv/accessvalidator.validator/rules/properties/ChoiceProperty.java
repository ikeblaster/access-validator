package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Collection;

/**
 * @author ike
 */
public class ChoiceProperty<T> extends Property<T> {

    private final Collection<T> choices;

    public ChoiceProperty(String id, T value, Collection<T> choices, String name, String description, String category) {
        super(id, value, name, description, category);
        this.choices = choices;
    }

    public Collection<T> getChoices() {
        return this.choices;
    }

    @Override
    public void setValue(T value) {
        if(!this.choices.contains(value)) {
            throw new RuntimeException("Unknown choice");
        }

        super.setValue(value);
    }

}
