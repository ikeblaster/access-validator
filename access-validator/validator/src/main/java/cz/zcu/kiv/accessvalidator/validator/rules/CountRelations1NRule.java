package cz.zcu.kiv.accessvalidator.validator.rules;

import com.healthmarketscience.jackcess.Relationship;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbRelationRepository;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * Rule which checks the number of 1:N relations in database.
 * Excludes relations to junction tables (M:N relations).
 *
 * @author Vojtech Kinkor
 */
public class CountRelations1NRule extends Rule {

    /**
     * Operator for comparing found number of relations.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of relations.
     */
    private Property<Integer> count;

    /**
     * Rule which checks the number of 1:N relations in database.
     * Excludes relations to junction tables (M:N relations).
     */
    public CountRelations1NRule() {
        super();

        String desc = "Vyhledá všechny 1:N relace, vynechá relace na mezitabulky tvořící M:N relace.";

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:N relací", desc, this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", desc, this.getGenericLabel()
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of 1:N relations is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository tableRepository = accdb.getTableRepository();
        tableRepository.filterMNJunctionTables();
        Set<String> junctionTables = tableRepository.getTables();

        AccdbRelationRepository repository = accdb.getRelationRepository();
        repository.filter1NRelations();

        Set<Relationship> foundRelations = repository.getRelations();
        foundRelations.removeIf(relationship -> junctionTables.contains(relationship.getToTable().getName()));

        return this.countOp.getValue().compare(foundRelations.size(), this.count.getValue());
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet relací 1:N";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Počet relací 1:N " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
