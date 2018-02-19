package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import java.util.Collection;

/**
 * @author ike
 */
public class ChoiceProperty extends Property {

    private final Collection<Object> choices;

    public ChoiceProperty(String id, Object value, Collection<Object> choices, String name, String description, String category) {
        super(id, value, name, description, category);
        this.choices = choices;
    }

    public Collection<Object> getChoices() {
        return this.choices;
    }

    @Override
    public void setValue(Object value) {
        if(!this.choices.contains(value)) {
            throw new RuntimeException("Unknown choice");
        }

        super.setValue(value);
    }

}
