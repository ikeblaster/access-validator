package cz.zcu.kiv.accessvalidator.validator.rules;

import com.healthmarketscience.jackcess.Relationship;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbRelationRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * @author ike
 */
public class Relation11ExistsRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public Relation11ExistsRule() {
        super();

        String group = "Počet 1:1 relací";

        this.countOp = new ChoiceProperty<>(
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:1 relací", "Operátor pro ověření počtu nalezených relací", group);

        this.count = new Property<>(
                "count", Integer.valueOf(1),
                "...", "Počet 1:1 relací mezi tabulkami", group);

        this.properties.add(this.countOp);
        this.properties.add(this.count);
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbRelationRepository repository = accdb.getRelationRepository();
        repository.filter11Relations();

        Set<Relationship> foundTables = repository.getRelations();

        return this.countOp.getValue().compare(foundTables.size(), this.count.getValue());
    }

    @Override
    public String toString() {
        return "Počet relací 1:1";
    }

}
