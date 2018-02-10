package cz.zcu.kiv.accessvalidator.configurator.rules;


import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public abstract class Rule {

    protected Set<RuleProperty> properties = new HashSet<>();

    public Rule newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    public Set<RuleProperty> getProperties() {
        return Collections.unmodifiableSet(this.properties);
    }


    public abstract String toString();

}
