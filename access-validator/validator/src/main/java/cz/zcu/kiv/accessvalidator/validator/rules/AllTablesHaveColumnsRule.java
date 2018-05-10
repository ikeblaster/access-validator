package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

/**
 * Rule which checks whether all tables in the database have a column defined by criteria.
 *
 * @author Vojtech Kinkor
 */
public class AllTablesHaveColumnsRule extends Rule {

    /**
     * Operator for comparing found number of columns.
     */
    private ChoiceProperty<ComparisonOperator> countOp;

    /**
     * Desired number of columns.
     */
    private Property<Integer> count;

    /**
     * Desired type of columns.
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
     * Rule which checks whether all tables in the database have a column defined by criteria.
     */
    public AllTablesHaveColumnsRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                "count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet sloupců", "Operátor pro ověření počtu řádků v tabulkách", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                "count",
                Integer.class, 1,
                "...", "Všechny tabulky v databázi musí obsahovat zadaný počet sloupců", this.getGenericLabel()
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
     * Checks database against the rule. Rule is satisfied when desired number of columns defined by criteria is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        int tablesInDb = repository.getTables().size();

        repository.filterByColumnCount(this.count.getValue(), this.countOp.getValue(), this.columnName.getValue(), this.columnType.getValue(), this.columnIsPrimary.getValue());

        return repository.getTables().size() == tablesInDb;
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Počet sloupců v každé tabulce";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        String details = "";

        if (this.columnType.getValue() != ColumnType._ANY) {
            details += " typu '" + this.columnType.getValue().toString() + "'";
        }
        if (!this.columnName.getValue().isEmpty()) {
            details += " s názvem '" + this.columnName.getValue() + "'";
        }
        if (this.columnIsPrimary.getValue() != YesNoType._ANY) {
            details += ", které " + (this.columnIsPrimary.getValue() == YesNoType.YES ? "jsou" : "nejsou") + " primárním klíčem,";
        }

        return "Počet sloupců" + details + " v každé tabulce " + this.countOp.getValue() + " " + this.count.getValue();
    }


}
