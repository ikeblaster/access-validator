package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class RelationExistsRule extends Rule {

    private final Property<String> propName;

    public RelationExistsRule() {
        super();

        this.propName = new Property<>(
                "name", "",
                "Název tabulky", "Název tabulky", "Název tabulky");

        this.properties.add(this.propName);
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
