package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.*;

import java.util.Set;

/**
 * Rule which does complex table search in database using multiple criteria.
 *
 * @author ike
 */
public class ComplexRule extends Rule {

    /**
     * Operator for comparing found number of tables.
     */
    private ChoiceProperty<ComparisonOperator> tablesCountOp;

    /**
     * Desired number of tables.
     */
    private Property<Integer> tablesCount;

    /**
     * Desired name of table (exact match).
     */
    private Property<String> tablesByName;

    /**
     * Operator for comparing found number of columns in tables.
     */
    private ChoiceProperty<ComparisonOperator> columnsCountOp;

    /**
     * Desired number of columns in tables.
     */
    private Property<Integer> columnsCount;

    /**
     * Desired name of columns in tables (exact match).
     */
    private Property<String> columnsByName;

    /**
     * Desired data type of columns in tables.
     */
    private ChoiceProperty<ColumnType> columnsByType;

    /**
     * Desired state of columns in tables by being a primary key.
     */
    private ChoiceProperty<YesNoType> columnIsPrimary;

    /**
     * Rule which does complex table search in database using multiple criteria.
     */
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

    /**
     * Checks database against the rule.
     * Rule is satisfied when desired number of tables having desired name
     * and columns (their count, type, name, being primary key) is found.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
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

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Komplexní vyhledání tabulek";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Komplexní vyhledání tabulek";
    }

}
