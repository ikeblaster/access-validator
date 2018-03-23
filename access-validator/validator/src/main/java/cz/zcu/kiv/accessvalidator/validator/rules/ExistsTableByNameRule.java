package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class ExistsTableByNameRule extends Rule {

    private Property<String> name;

    public ExistsTableByNameRule() {
        super();

        String group = "Název tabulky";

        this.name = this.addProperty(new Property<>(
                "name",
                String.class, "",
                group, group, group
        ));
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        repository.filterByName(this.name.getValue());

        return repository.getTables().size() > 0;
    }

    @Override
    public String getGenericLabel() {
        return "Existence tabulky s názvem";
    }

    @Override
    public String toString() {
        return "Existuje tabulka s názvem '" + this.name.getValue() + "'";
    }

}
