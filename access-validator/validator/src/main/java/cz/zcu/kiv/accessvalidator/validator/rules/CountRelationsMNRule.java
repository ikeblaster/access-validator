package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * Rule which checks the number of M:N relations in database (i.e. number of junction tables in database).
 *
 * @author ike
 */
public class CountRelationsMNRule extends Rule {

    /**
     * Operator for comparing found number of relations.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of relations.
     */
    private Property<Integer> count;

    /**
     * Rule which checks the number of M:N relations in database (i.e. number of junction tables in database).
     */
    public CountRelationsMNRule() {
        super();

        String desc = "Vyhledá všechny spojovací mezitabulky tvořící M:N relace" +
                "(tj. tabulky, které mají relace 1:N mířící na primární klíče dvou jiných tabulek)";

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet M:N relací", desc, this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", desc, this.getGenericLabel()
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of M:N relations is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();
        repository.filterMNJunctionTables();

        Set<String> foundTables = repository.getTables();

        return this.countOp.getValue().compare(foundTables.size(), this.count.getValue());
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet relací M:N";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Počet relací M:N " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
