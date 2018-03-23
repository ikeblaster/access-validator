package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

/**
 * @author ike
 */
public class AllTablesHaveColumnsRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;
    private ChoiceProperty<ColumnType> columnType;
    private Property<String> columnName;
    private ChoiceProperty<YesNoType> columnIsPrimary;

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

    @SuppressWarnings("Duplicates")
    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        int tablesInDb = repository.getTables().size();

        repository.filterByColumnCount(this.count.getValue(), this.countOp.getValue(), this.columnName.getValue(), this.columnType.getValue(), this.columnIsPrimary.getValue());

        return repository.getTables().size() == tablesInDb;
    }

    @Override
    public String getGenericLabel() {
        return "Počet sloupců v každé tabulce";
    }

    @Override
    public String toString() {
        String details = "";

        if (this.columnType.getValue() != ColumnType._ANY) {
            details += "typu '" + this.columnType.getValue().toString() + "' ";
        }
        if (!this.columnName.getValue().isEmpty()) {
            details += "s názvem '" + this.columnName.getValue() + "' ";
        }

        return "Počet sloupců " + details + "v každé tabulce " + this.countOp.getValue() + " " + this.count.getValue();
    }


}
