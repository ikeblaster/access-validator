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
public class RelationMNExistsRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public RelationMNExistsRule() {
        super();

        String group = "Počet relací M:N";

        this.countOp = new ChoiceProperty<>(
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet M:N relací", "Operátor pro ověření počtu nalezených relací", group);

        this.count = new Property<>(
                "count", Integer.valueOf(1),
                "...", "Počet M:N relací", group);

        this.properties.add(this.countOp);
        this.properties.add(this.count);
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();
        repository.filterMNJunctionTables();

        Set<String> foundTables = repository.getTables();

        return this.countOp.getValue().compare(foundTables.size(), this.count.getValue());
    }

    @Override
    public String toString() {
        return "Počet relací M:N";
    }

}
