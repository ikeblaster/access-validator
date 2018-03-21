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
public class CountRelations11Rule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public CountRelations11Rule() {
        super();

        String group = "Počet 1:1 relací";

        this.countOp = this.addProperty(new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:1 relací", "Operátor pro ověření počtu nalezených relací", group
        ));
        this.count = this.addProperty(new Property<>(
                Integer.class,
                "count", Integer.valueOf(1),
                "...", "Počet 1:1 relací mezi tabulkami", group
        ));
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbRelationRepository repository = accdb.getRelationRepository();
        repository.filter11Relations();

        Set<Relationship> foundRelations = repository.getRelations();

        return this.countOp.getValue().compare(foundRelations.size(), this.count.getValue());
    }

    @Override
    public String getGenericLabel() {
        return "Počet relací 1:1";
    }

    @Override
    public String toString() {
        return "Počet relací 1:1 " + this.countOp.getValue().toString() + " " + this.count.getValue();
    }

}
