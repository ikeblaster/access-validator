package cz.zcu.kiv.accessvalidator.validator.rules;


import cz.zcu.kiv.accessvalidator.validator.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.beans.InvalidationListener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author ike
 */
public abstract class Rule {

    protected Collection<Property<?>> properties = new ArrayList<>();

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
            if(id.equals(property.getId())) {
                return property;
            }
        }
        return null;
    }

    public void onChange(InvalidationListener listener) {
        throw new UnsupportedOperationException();
    }

    public abstract boolean check(Accdb accdb);

    public abstract String toString();

}
