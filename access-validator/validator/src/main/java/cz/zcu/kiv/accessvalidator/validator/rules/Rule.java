package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.beans.InvalidationListener;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author ike
 */
public abstract class Rule {

    protected Collection<Property<?>> properties = new ArrayList<>();

    public Rule() {

    }

    public Rule newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Property<?>> getProperties() {
        return Collections.unmodifiableCollection(this.properties);
    }

    public Property<?> getProperty(String id) {
        for(Property<?> property : this.properties) {
            if(Objects.equals(id, property.getId())) {
                return property;
            }
        }
        return null;
    }

    public void onChange(InvalidationListener listener) {
        for(Property<?> property : this.properties) {
            property.onChange(listener);
        }
    }

    public abstract boolean check(Accdb accdb);

    public abstract String getGenericLabel();

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;

        return Objects.equals(this.properties, rule.properties) &&
                Objects.equals(this.toString(), rule.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString(), this.properties);
    }


    protected <T extends Property> T addProperty(T prop) {
        this.properties.add(prop);
        return prop;
    }
}
