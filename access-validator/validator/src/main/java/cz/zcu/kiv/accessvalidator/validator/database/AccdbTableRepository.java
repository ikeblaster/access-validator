package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */

public class AccdbTableRepository {

    private Database db;
    private Set<String> tables = Collections.emptySet();

    AccdbTableRepository(Database db) {
        this.db = db;
        try {
            this.tables = new HashSet<>(db.getTableNames());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterByName(String tableName) {
        this.tables.retainAll(Collections.singleton(tableName));
    }

    public void filterByColumnCount(int columnCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getColumnCount(), columnCount));
    }

    public void filterByColumnName(String columnName) {
        this.tables.removeIf(tableName -> !this.tableHasColumn(tableName, columnName));
    }

    public void filterByColumnType(String columnName) {
        // TODO: implement, ENUM do ComplexRule, zde porovnání
        //this.tables.removeIf(name -> !((TableImpl) this.getTable(name)).getColumn(columnName).getType().);
    }

    public void filterByRowsCount(int rowsCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getRowCount(), rowsCount));
    }

    public Set<String> getTables() {
        return this.tables;
    }


    private Table getTable(String name) {
        try {
            return this.db.getTable(name);
        } catch (Exception e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB changed
            throw new NullPointerException();
        }
    }

    private boolean tableHasColumn(String table, String column) {
        try {
            return this.db.getTable(table).getColumn(column) != null;
        }
        catch (Exception e) {
            return false;
        }
    }

}
