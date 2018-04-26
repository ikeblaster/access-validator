package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.beans.InvalidationListener;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Represents a rule for checking ACCDB databse.
 *
 * @author ike
 */
public abstract class Rule {

    /**
     * Collection of rule properties.
     */
    protected Collection<Property<?>> properties = new ArrayList<>();

    /**
     * Represents a rule for checking ACCDB databse.
     */
    public Rule() {
    }

    /**
     * Gets a new instance of current rule.
     *
     * @return New instance of current rule.
     */
    public Rule newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets unmodifiable collection of rules properties (property values are modifiable).
     *
     * @return Unmodifiable collection of rules properties.
     */
    public Collection<Property<?>> getProperties() {
        return Collections.unmodifiableCollection(this.properties);
    }

    /**
     * Gets property based on its internal ID.
     *
     * @param id Internal ID of property.
     * @return Requested property or {@code null} when not found.
     */
    public Property<?> getProperty(String id) {
        for(Property<?> property : this.properties) {
            if(Objects.equals(id, property.getId())) {
                return property;
            }
        }
        return null;
    }

    /**
     * Adds change listener to all properties.
     *
     * @param listener Listener.
     */
    public void onChange(InvalidationListener listener) {
        for(Property<?> property : this.properties) {
            property.onChange(listener);
        }
    }

    /**
     * Checks database against the rule.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    public abstract boolean check(Accdb accdb);

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    public abstract String getGenericLabel();

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    public abstract String toString();

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
        Rule rule = (Rule) o;

        return Objects.equals(this.properties, rule.properties) &&
                Objects.equals(this.toString(), rule.toString());
    }

    /**
     * Computes a hash value based on {@code toString()} and properties.
     *
     * @return A hash value of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.toString(), this.properties);
    }

    /**
     * Adds new property to the collection and immediately returns it for chain processing.
     *
     * @param property New property.
     * @param <T> Type of property.
     * @return The {@code property} argument.
     */
    protected <T extends Property> T addProperty(T property) {
        this.properties.add(property);
        return property;
    }

}
