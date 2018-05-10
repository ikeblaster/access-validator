package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * Rule which checks whether a table with specified name exists in database.
 *
 * @author Vojtech Kinkor
 */
public class ExistsTableByNameRule extends Rule {

    /**
     * Desired name of table (exact match).
     */
    private Property<String> name;

    /**
     * Rule which checks whether a table with specified name exists in database.
     */
    public ExistsTableByNameRule() {
        super();

        String group = "Název tabulky";

        this.name = this.addProperty(new Property<>(
                "name",
                String.class, "",
                group, group, group
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when table with specified name is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        repository.filterByName(this.name.getValue());

        return repository.getTables().size() > 0;
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Existence tabulky s názvem";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Existuje tabulka s názvem '" + this.name.getValue() + "'";
    }

}
