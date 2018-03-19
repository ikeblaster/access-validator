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
 * @author ike
 */
public class Relation1NExistsRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public Relation1NExistsRule() {
        super();

        String group = "Počet relací 1:N";
        String desc = "Vyhledá všechny 1:N relace, vynechá relace na mezitabulky tvořící M:N relace.";

        this.countOp = new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:N relací", desc, group);

        this.count = new Property<>(
                Integer.class,
                "count", Integer.valueOf(1),
                "...", desc, group);

        this.properties.add(this.countOp);
        this.properties.add(this.count);
    }

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

    @Override
    public String toString() {
        return "Počet relací 1:N";
    }

}
