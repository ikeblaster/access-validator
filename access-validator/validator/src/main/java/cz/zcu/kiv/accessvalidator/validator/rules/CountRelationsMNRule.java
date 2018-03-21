package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * @author ike
 */
public class CountRelationsMNRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public CountRelationsMNRule() {
        super();

        String group = "Počet relací M:N";
        String desc = "Vyhledá všechny spojovací mezitabulky tvořící M:N relace" +
                "(tj. tabulky, které mají 2 relace 1:N mířící na primární klíče)";

        this.countOp = this.addProperty(new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet M:N relací", desc, group
        ));
        this.count = this.addProperty(new Property<>(
                Integer.class,
                "count", Integer.valueOf(1),
                "...", desc, group
        ));
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();
        repository.filterMNJunctionTables();

        Set<String> foundTables = repository.getTables();

        return this.countOp.getValue().compare(foundTables.size(), this.count.getValue());
    }

    @Override
    public String getGenericLabel() {
        return "Počet relací M:N";
    }

    @Override
    public String toString() {
        return "Počet relací M:N " + this.countOp.getValue().toString() + " " + this.count.getValue();
    }

}
