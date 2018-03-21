package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

import java.util.Set;

/**
 * @author ike
 */
public class CountTablesWithColumnRule extends Rule {

    private ChoiceProperty<ComparisonOperator> countOp;
    private Property<Integer> count;
    private ChoiceProperty<ColumnType> columnType;
    private Property<String> columnName;
    private ChoiceProperty<YesNoType> columnIsPrimary;


    public CountTablesWithColumnRule() {
        super();

        this.countOp = this.addProperty(new ChoiceProperty<>(
                ComparisonOperator.class,
                "count_op", ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet tabulek", "Operátor pro ověření počtu nalezených tabulek", this.getGenericLabel()
        ));
        this.count = this.addProperty(new Property<>(
                Integer.class,
                "count", 1,
                "...", "Počet tabulek se sloupci dle kritérií", this.getGenericLabel()
        ));

        this.columnType = this.addProperty(new ChoiceProperty<>(
                ColumnType.class,
                "column_type", ColumnType._ANY, ColumnType.getChoices(),
                "Typ sloupce", "Ověří, zda v tabulce existuje sloupec daného typu a názvu", this.getGenericLabel()
        ));
        this.columnName = this.addProperty(new Property<>(
                String.class,
                "column_name", "",
                "Název sloupce", "Ověří, zda v tabulce existuje sloupec daného typu a názvu", this.getGenericLabel()
        ));
        this.columnIsPrimary = this.addProperty(new ChoiceProperty<>(
                YesNoType.class,
                "column_primary", YesNoType._ANY, YesNoType.getChoices(),
                "Primární klíč", "Ověří, zda je sloupec součástí primárního klíče", this.getGenericLabel()
        ));

    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        repository.filterByColumnCount(1, ComparisonOperator.GTE, this.columnName.getValue(), this.columnType.getValue(), this.columnIsPrimary.getValue());

        Set<String> foundTables = repository.getTables();
        return this.countOp.getValue().compare(foundTables.size(), this.count.getValue());
    }

    @Override
    public String getGenericLabel() {
        return "Počet tabulek se sloupcem";
    }

    @Override
    public String toString() {
        String details = "";

        if(this.columnType.getValue() != ColumnType._ANY) {
            details += "typu '" + this.columnType.getValue().toString() + "' ";
        }
        if(!this.columnName.getValue().isEmpty()) {
            details += "s názvem '" + this.columnName.getValue() + "' ";
        }

        return "Počet tabulek se sloupcem " + details + this.countOp.getValue().toString() + " " + this.count.getValue();
    }


}
