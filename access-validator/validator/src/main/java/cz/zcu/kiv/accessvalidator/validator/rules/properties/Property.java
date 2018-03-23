package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

/**
 * @author ike
 */
public class Property<T> {

    private final Class<T> type;
    private final String id;
    private final SimpleObjectProperty<T> value;

    private String name;
    private String description;
    private String category;


    public Property(String id, Class<T> type, T value, String name, String description, String category) {
        this.type = type;
        this.id = id;
        this.value = new SimpleObjectProperty<>(value);
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Class<T> getType() {
        return this.type;
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

    public void setRawValue(Object value) {
        this.value.setValue(this.type.cast(value));
    }

    public void onChange(InvalidationListener listener) {
        this.value.addListener(listener);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Property<?> property = (Property<?>) o;
        return Objects.equals(this.type, property.type) &&
                Objects.equals(this.id, property.id) &&
                Objects.equals(this.value.getValue(), property.value.getValue()) &&
                Objects.equals(this.name, property.name) &&
                Objects.equals(this.description, property.description) &&
                Objects.equals(this.category, property.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.value.getValue(), this.name, this.description, this.category);
    }
}
