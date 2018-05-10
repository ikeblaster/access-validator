package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;


/**
 * @author Vojtech Kinkor
 */
public class _RuleWithUnsupportedProperty extends Rule {

    public _RuleWithUnsupportedProperty() {
        this.properties.add(new Property<>("id_bln", Boolean.class, true, "", "", ""));
        this.properties.add(new Property<>("id_unsupported", Double.class, 1d, "", "", ""));
    }

    @Override
    public boolean check(Accdb accdb) {
        return false;
    }

    @Override
    public String getGenericLabel() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

}
