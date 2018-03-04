package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;

import java.util.Set;

/**
 * @author ike
 */
public class ComplexRule extends Rule {
    private ChoiceProperty<ComparisonOperator> propTablesCountOp;
    private Property<Integer> propTablesCount;
    private Property<String> propTablesByName;
    private ChoiceProperty<ComparisonOperator> propColumnsCountOp;
    private Property<Integer> propColumnsCount;
    private Property<String> propColumnsByName;
    private Property<String> propColumnsByType;

    public ComplexRule() {
        super();

        this.propTablesCountOp = new ChoiceProperty<>(
                "tables_count_op", ComparisonOperator.GTE, ComparisonOperator.asList(),
                "Počet tabulek", "Operátor pro ověření počtu tabulek", "1. Počet nalezených tabulek");

        this.propTablesCount = new Property<>(
                "tables_count", Integer.valueOf(1),
                "...", "Počet hledaných tabulek", "1. Počet nalezených tabulek");

        this.propTablesByName = new Property<>(
                "tables_byname", "",
                "Název tabulky", "Název tabulky", "2. Filtrování dle názvu tabulky");

        this.propColumnsCountOp = new ChoiceProperty<>(
                "columns_count_op", ComparisonOperator.GTE, ComparisonOperator.asList(),
                "Počet sloupců v tabulce", "Operátor pro ověření počtu tabulek", "3. Filtrování dle počtu sloupců");

        this.propColumnsCount = new Property<>(
                "columns_count", Integer.valueOf(1),
                "...", "Alespoň jedna nalezená tabulka má zadaný počet sloupců", "3. Filtrování dle počtu sloupců");

        this.propColumnsByName = new Property<>(
                "columns_byname", "",
                "Název sloupce", "Existuje tabulka se sloupcem dle zadaného názvu.", "4. Filtrování dle existence sloupce");

        this.propColumnsByType = new Property<>(
                "columns_bytype", "",
                "Datový typ sloupce", "Existuje tabulka se sloupcem dle zadaného datového typu.", "4. Filtrování dle existence sloupce");

        this.properties.add(this.propTablesCountOp);
        this.properties.add(this.propTablesCount);
        this.properties.add(this.propTablesByName);
        this.properties.add(this.propColumnsCountOp);
        this.properties.add(this.propColumnsCount);
        this.properties.add(this.propColumnsByName);
        this.properties.add(this.propColumnsByType);
    }

    @Override
    public boolean check(Accdb accdb) {
        AccdbTableRepository repository = accdb.getTableRepository();

        if(!this.propTablesByName.getValue().isEmpty()) repository.filterByName(this.propTablesByName.getValue());
        repository.filterByColumnCount(this.propColumnsCount.getValue(), this.propColumnsCountOp.getValue());
        if(!this.propColumnsByName.getValue().isEmpty()) repository.filterByColumnName(this.propColumnsByName.getValue());
        if(!this.propColumnsByType.getValue().isEmpty()) repository.filterByColumnType(this.propColumnsByType.getValue());

        Set<String> foundTables = repository.getTables();

        return this.propTablesCountOp.getValue().compare(foundTables.size(), this.propTablesCount.getValue());
    }

    @Override
    public String toString() {
        return "Komplexní kontrola";
    }

}
