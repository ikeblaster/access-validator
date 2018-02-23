package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author ike
 */
public class Property<T> {

    private final String id;
    private javafx.beans.property.Property<T> value;

    private String name;
    private String description;
    private String category;


    public Property(String id, T value, String name, String description, String category) {
        this.id = id;
        this.value = new SimpleObjectProperty<>(value);
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Class<?> getType() {
        return this.getValue().getClass();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public T getValue() {
        return this.value.getValue();
    }

    public void setValue(T value) {
        this.value.setValue(value);
    }

    public void onChange(InvalidationListener listener) {
        this.value.addListener(listener);
    }

}
