package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class TableByNameExistsRule extends Rule {

    private Property<String> propName;

    public TableByNameExistsRule() {
        super();

        this.propName = new Property<>(
                "name", "",
                "Název tabulky", "Název tabulky", "Název tabulky");

        this.properties.add(this.propName);
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        repository.filterByName(this.propName.getValue());

        return repository.getTables().size() > 0;
    }

    @Override
    public String toString() {
        return "Existence tabulky dle názvu";
    }

}
