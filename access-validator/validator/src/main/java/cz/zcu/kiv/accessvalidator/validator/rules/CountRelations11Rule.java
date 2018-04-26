package cz.zcu.kiv.accessvalidator.validator.rules;

import com.healthmarketscience.jackcess.Relationship;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbRelationRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * Rule which checks the number of 1:1 relations in database.
 *
 * @author ike
 */
public class CountRelations11Rule extends Rule {

    /**
     * Operator for comparing found number of relations.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of relations.
     */
    private Property<Integer> count;

    /**
     * Rule which checks the number of 1:1 relations in database.
     */
    public CountRelations11Rule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:1 relací", "Operátor pro ověření počtu nalezených relací", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", "Počet 1:1 relací mezi tabulkami", this.getGenericLabel()
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of 1:1 relations is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbRelationRepository repository = accdb.getRelationRepository();
        repository.filter11Relations();

        Set<Relationship> foundRelations = repository.getRelations();

        return this.countOp.getValue().compare(foundRelations.size(), this.count.getValue());
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet relací 1:1";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Počet relací 1:1 " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
