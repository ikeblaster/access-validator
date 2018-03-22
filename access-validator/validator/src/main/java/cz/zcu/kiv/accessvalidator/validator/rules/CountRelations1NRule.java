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
public class CountRelations1NRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;

    public CountRelations1NRule() {
        super();

        String desc = "Vyhledá všechny 1:N relace, vynechá relace na mezitabulky tvořící M:N relace.";

        this.countOp = this.addProperty(new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet 1:N relací", desc, this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                Integer.class,
                "count", 1,
                "...", desc, this.getGenericLabel()
        ));
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
    public String getGenericLabel() {
        return "Počet relací 1:N";
    }

    @Override
    public String toString() {
        return "Počet relací 1:N " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
