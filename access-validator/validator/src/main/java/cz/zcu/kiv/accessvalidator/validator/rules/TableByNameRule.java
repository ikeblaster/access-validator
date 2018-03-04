package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * @author ike
 */
public class TableByNameRule extends Rule {

    private Property<String> propName;

    public TableByNameRule() {
        super();

        this.propName = new Property<>(
                "name", "",
                "Název tabulky", "Název tabulky", "Název tabulky");

        this.properties.add(this.propName);
    }

    @Override
    public boolean check(Accdb accdb) {
        Accdb.TableRepository repository = accdb.getTableRepository();

        repository.filterByName(this.propName.getValue());

        return repository.getTables().size() > 0;
    }

    @Override
    public String toString() {
        return "Existence tabulky dle názvu";
    }

}
