package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

/**
 * Generic property.
 *
 * @author Vojtech Kinkor
 */
public class Property<T> {

    /**
     * Data type of property.
     */
    private final Class<T> type;

    /**
     * ID of property.
     */
    private final String id;

    /**
     * Current value of property.
     * Uses {@code SimpleObjectProperty} because of it's {@code Observable} interface.
     */
    private final SimpleObjectProperty<T> value;

    /**
     * Name for use in UI.
     */
    private String name;

    /**
     * Description for use in UI.
     */
    private String description;

    /**
     * Description for use in UI.
     */
    private String category;

    /**
     * Generic property.
     *
     * @param id Internal ID.
     * @param type Value data type.
     * @param value Default value.
     * @param name Name of property.
     * @param description Description of property.
     * @param category Category of property
     */
    public Property(String id, Class<T> type, T value, String name, String description, String category) {
        this.type = type;
        this.id = id;
        this.value = new SimpleObjectProperty<>(value);
        this.name = name;
        this.description = description;
        this.category = category;
    }

    /**
     * Gets data type of property.
     *
     * @return Data type of property.
     */
    public Class<T> getType() {
        return this.type;
    }

    /**
     * Gets ID.
     *
     * @return ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets name of property for use in UI.
     *
     * @return Name of property.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets description of property for use in UI.
     *
     * @return Description of property.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets category of property for use in UI.
     *
     * @return Category of property.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets value of property.
     *
     * @return Value of property.
     */
    public T getValue() {
        return this.value.getValue();
    }

    /**
     * Sets new value to property.
     *
     * @param value New value.
     */
    public void setValue(T value) {
        this.value.setValue(value);
    }

    /**
     * Sets new value to property casted to the type of property.
     *
     * @param value New value.
     */
    public void setRawValue(Object value) {
        this.value.setValue(this.type.cast(value));
    }

    /**
     * Adds change listener to property value.
     *
     * @param listener Listener.
     */
    public void onChange(InvalidationListener listener) {
        this.value.addListener(listener);
    }

    /**
     * Compares this object to other object.
     *
     * @param o Other object.
     * @return {@code true} if the argument is equal to this object and {@code false} otherwise.
     */
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

    /**
     * Computes a hash value.
     *
     * @return A hash value of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.value.getValue(), this.name, this.description, this.category);
    }
}
