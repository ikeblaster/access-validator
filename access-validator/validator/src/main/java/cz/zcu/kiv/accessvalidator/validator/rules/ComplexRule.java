package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

import java.util.Set;

/**
 * @author ike
 */
public class ComplexRule extends Rule {
    private ChoiceProperty<ComparisonOperator> tablesCountOp;
    private Property<Integer> tablesCount;
    private Property<String> tablesByName;
    private ChoiceProperty<ComparisonOperator> columnsCountOp;
    private Property<Integer> columnsCount;
    private Property<String> columnsByName;
    private ChoiceProperty<ColumnType> columnsByType;
    private ChoiceProperty<YesNoType> columnIsPrimary;

    public ComplexRule() {
        super();

        this.tablesCountOp = this.addProperty(new ChoiceProperty<>(
                "tables_count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet tabulek", "Operátor pro ověření počtu tabulek", "1. Počet nalezených tabulek"
        ));
        this.tablesCount = this.addProperty(new Property<>(
                "tables_count",
                Integer.class, 1,
                "...", "Počet hledaných tabulek", "1. Počet nalezených tabulek"
        ));

        this.tablesByName = this.addProperty(new Property<>(
                "tables_byname",
                String.class, "",
                "Název tabulky", "Název tabulky", "2. Filtrování dle názvu tabulky"
        ));

        this.columnsCountOp = this.addProperty(new ChoiceProperty<>(
                "columns_count_op",
                ComparisonOperator.class, ComparisonOperator.GTE, ComparisonOperator.getChoices(),
                "Počet sloupců v tabulce", "Operátor pro ověření počtu sloupců", "3. Filtrování dle počtu sloupců"
        ));
        this.columnsCount = this.addProperty(new Property<>(
                "columns_count",
                Integer.class, 1,
                "...", "Alespoň jedna nalezená tabulka má zadaný počet sloupců", "3. Filtrování dle počtu sloupců"
        ));

        this.columnsByName = this.addProperty(new Property<>(
                "columns_byname",
                String.class, "",
                "Název sloupce", "Existuje tabulka se sloupcem dle zadaného názvu.", "4. Filtrování dle existence sloupce"
        ));
        this.columnsByType = this.addProperty(new ChoiceProperty<>(
                "column_type",
                ColumnType.class, ColumnType._ANY, ColumnType.getChoices(),
                "Typ sloupce", "Existuje tabulka se sloupcem dle zadaného datového typu.", "4. Filtrování dle existence sloupce"
        ));
        this.columnIsPrimary = this.addProperty(new ChoiceProperty<>(
                "column_primary",
                YesNoType.class, YesNoType._ANY, YesNoType.getChoices(),
                "Primární klíč", "Ověří, zda je sloupec součástí primárního klíče", "4. Filtrování dle existence sloupce"
        ));

    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        if(!this.tablesByName.getValue().isEmpty()) {
            repository.filterByName(this.tablesByName.getValue());
        }

        repository.filterByColumnCount(this.columnsCount.getValue(), this.columnsCountOp.getValue());
        repository.filterByColumnExistence(this.columnsByName.getValue(), this.columnsByType.getValue(), this.columnIsPrimary.getValue());

        Set<String> foundTables = repository.getTables();
        return this.tablesCountOp.getValue().compare(foundTables.size(), this.tablesCount.getValue());
    }

    @Override
    public String getGenericLabel() {
        return "Komplexní vyhledání tabulek";
    }

    @Override
    public String toString() {
        return "Komplexní vyhledání tabulek";
    }

}
