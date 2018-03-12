package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.*;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void filterMNJunctionTables() {
        this.tables.removeIf(tableName -> {
            List<Relationship> relationships = this.getRelationships(tableName);
            int junction = 0;

            for(Relationship relationship : relationships) {
                if(relationship.isOneToOne() || relationship.getFromTable().getName().equals(tableName)) {
                    continue;
                }

                List<Column> primaryKeyColumns = relationship.getFromTable().getPrimaryKeyIndex().getColumns().stream().map(Index.Column::getColumn).collect(Collectors.toList());
                
                if(primaryKeyColumns.containsAll(relationship.getFromColumns())) {
                    junction++;
                }
            }

            return junction != 2; // only one M:N relation is ok
        });
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

    private List<Relationship> getRelationships(String name) {
        try {
            return this.db.getRelationships(this.db.getTable(name));
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
