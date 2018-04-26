package cz.zcu.kiv.accessvalidator.validator.rules;

import com.healthmarketscience.jackcess.query.Query;
import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbQueryRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.QueryType;

import java.util.Set;

/**
 * Rule which checks the number of queries in database.
 *
 * @author ike
 */
public class CountQueriesRule extends Rule {

    /**
     * Operator for comparing found number of queries.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of queries.
     */
    private Property<Integer> count;

    /**
     * Desired type of queries.
     */
    private ChoiceProperty<QueryType> type;

    /**
     * Rule which checks the number of queries in database.
     */
    public CountQueriesRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet dotazů", "Operátor pro ověření počtu nalezených dotazů", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", "Počet uložených dotazů v databázi", this.getGenericLabel()
        ));
        this.type = this.addProperty(new ChoiceProperty<>(
                "query_type",
                QueryType.class, QueryType._ANY, QueryType.getChoices(),
                "Typ dotazu", "Ověří, zda existuje dotaz zadaného typu", this.getGenericLabel()
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of queries is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbQueryRepository repository = accdb.getQueryRepository();

        if(this.type.getValue() != QueryType._ANY) {
            repository.filterByType(this.type.getValue());
        }

        Set<Query> foundQueries = repository.getQueries();

        return this.countOp.getValue().compare(foundQueries.size(), this.count.getValue());
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet dotazů";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        String details = "";
        if(this.type.getValue() != QueryType._ANY) {
            details += "typu '" + this.type.getValue() + "' ";
        }
        return "Počet dotazů " + details + this.countOp.getValue() + " " + this.count.getValue();
    }

}
