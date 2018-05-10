package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

/**
 * Rule which checks whether all tables in the database have a desired number of rows.
 *
 * @author Vojtech Kinkor
 */
public class AllTablesHaveRowsRule extends Rule {

    /**
     * Operator for comparing found number of rows.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of columns.
     */
    private Property<Integer> count;

    /**
     * Rule which checks whether all tables in the database have a desired number of rows.
     */
    public AllTablesHaveRowsRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet řádků", "Operátor pro ověření počtu řádků v tabulkách", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", "Všechny tabulky v databázi musí obsahovat zadaný počet řádků", this.getGenericLabel()
        ));
    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of rows is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        int tablesInDb = repository.getTables().size();
        repository.filterByRowCount(this.count.getValue(), this.countOp.getValue());

        return repository.getTables().size() == tablesInDb;
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet řádků v každé tabulce";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Počet řádků v každé tabulce " + this.countOp.getValue() + " " + this.count.getValue();
    }

}
