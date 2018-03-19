package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

public class _RuleWithUnsupportedProperty extends Rule {

    public _RuleWithUnsupportedProperty() {
        this.properties.add(new Property<>(Boolean.class, "id_bln", true, "", "", ""));
        this.properties.add(new Property<>(Double.class, "id_unsupported", 1d, "", "", ""));
    }

    @Override
    public boolean check(Accdb accdb) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

}
