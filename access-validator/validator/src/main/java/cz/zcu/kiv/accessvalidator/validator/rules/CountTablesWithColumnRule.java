package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

import java.util.Set;

/**
 * Rule which checks the number of tables having column defined by criteria in database.
 *
 * @author Vojtech Kinkor
 */
public class CountTablesWithColumnRule extends Rule {

    /**
     * Operator for comparing found number of tables.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of tables.
     */
    private Property<Integer> count;

    /**
     * Desired data type of column.
     */
    private ChoiceProperty<ColumnType> columnType;

    /**
     * Desired name of column (exact match).
     */
    private Property<String> columnName;

    /**
     * Desired state of column by being a primary key.
     */
    private ChoiceProperty<YesNoType> columnIsPrimary;

    /**
     * Rule which checks the number of tables having column defined by criteria in database.
     */
    public CountTablesWithColumnRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet tabulek", "Operátor pro ověření počtu nalezených tabulek", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", "Počet tabulek se sloupci dle kritérií", this.getGenericLabel()
        ));

        this.columnType = this.addProperty(new ChoiceProperty<>(
                "column_type",
                ColumnType.class, ColumnType._ANY, ColumnType.getChoices(),
                "Typ sloupce", "Ověří, zda v tabulce existuje sloupec daného typu a názvu", this.getGenericLabel()
        ));
        this.columnName = this.addProperty(new Property<>(
                "column_name",
                String.class, "",
                "Název sloupce", "Ověří, zda v tabulce existuje sloupec daného typu a názvu", this.getGenericLabel()
        ));
        this.columnIsPrimary = this.addProperty(new ChoiceProperty<>(
                "column_primary",
                YesNoType.class, YesNoType._ANY, YesNoType.getChoices(),
                "Primární klíč", "Ověří, zda je sloupec součástí primárního klíče", this.getGenericLabel()
        ));

    }

    /**
     * Checks database against the rule. Rule is satisfied when desired number of tables is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        repository.filterByColumnExistence(this.columnName.getValue(), this.columnType.getValue(), this.columnIsPrimary.getValue());

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
        return "Počet tabulek se sloupcem";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        String details = "";

        if(this.columnType.getValue() != ColumnType._ANY) {
            details += " typu '" + this.columnType.getValue().toString() + "'";
        }
        if(!this.columnName.getValue().isEmpty()) {
            details += " s názvem '" + this.columnName.getValue() + "'";
        }
        if (this.columnIsPrimary.getValue() != YesNoType._ANY) {
            details += ", který " + (this.columnIsPrimary.getValue() == YesNoType.YES ? "je" : "není") + " primárním klíčem";
        }

        return "Počet tabulek se sloupcem" + details + " " + this.countOp.getValue() + " " + this.count.getValue();
    }


}
