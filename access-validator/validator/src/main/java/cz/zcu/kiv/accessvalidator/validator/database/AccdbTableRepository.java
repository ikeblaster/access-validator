package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.*;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ColumnType;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.YesNoType;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ike
 */
public class AccdbTableRepository {

    private Database db;
    private Set<String> tables;

    AccdbTableRepository(Database db) {
        this.db = db;
        try {
            this.tables = new HashSet<>(db.getTableNames());
            this.tables.removeIf(table -> table.startsWith("~"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterByName(String tableName) {
        this.tables.retainAll(Collections.singleton(tableName));
    }

    public void filterByColumnCount(int columnCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getColumnCount(), columnCount));
    }

    public void filterByColumnCount(int columnCount, ComparisonOperator operator, String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTableColumnCountByCriteria(tableName, columnName, columnType, isPrimaryKey), columnCount));
    }

    public void filterByColumnExistence(String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        this.tables.removeIf(tableName -> this.getTableColumnCountByCriteria(tableName, columnName, columnType, isPrimaryKey) == 0);
    }

    public void filterByRowsCount(int rowsCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getRowCount(), rowsCount));
    }

    public void filterMNJunctionTables() {
        this.tables.removeIf(tableName -> !this.isMNJunctionTable(tableName));
    }

    public Set<String> getTables() {
        return this.tables;
    }


    private Table getTable(String tableName) {
        try {
            return this.db.getTable(tableName);
        } catch (IOException e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB file changed
            throw new RuntimeException(e);
        }
    }

    private List<Relationship> getRelationships(String tableName) {
        try {
            return this.db.getRelationships(this.db.getTable(tableName));
        } catch (IOException e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB file changed
            throw new RuntimeException(e);
        }
    }

    private int getTableColumnCountByCriteria(String tableName, String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        Table table = this.getTable(tableName);
        List<? extends Column> columns = new ArrayList<>(table.getColumns());

        if(columnName != null && !columnName.isEmpty()) {
            columns.removeIf(col -> !col.getName().equals(columnName)); // remove columns with different names than columnName
        }
        if(columnType != null && columnType != ColumnType._ANY) {
            columns.removeIf(col -> !columnType.compare(col));  // remove columns with different types than columnType
        }
        if(isPrimaryKey != null && isPrimaryKey != YesNoType._ANY) {
            List<Column> primaryKeyColumns = table.getPrimaryKeyIndex().getColumns().stream().map(Index.Column::getColumn).collect(Collectors.toList());
            columns.removeIf(col -> (isPrimaryKey == YesNoType.YES) ^ primaryKeyColumns.contains(col)); // either we are looking for primary key and col isn't one... or the exact opposite
        }

        return columns.size();
    }

    private boolean isMNJunctionTable(String tableName) {
        List<Relationship> relationships = this.getRelationships(tableName);
        int foreignTables = 0;

        for(Relationship relationship : relationships) {
            // skip 1:1 relations and relations, where current table acts as foreign (then it's not part of M:N relation)
            if(relationship.isOneToOne() || relationship.getFromTable().getName().equals(tableName)) {
                continue;
            }

            // get primary key columns from "From" table (that's not junction table)
            List<Column> primaryKeyColumns = relationship.getFromTable().getPrimaryKeyIndex().getColumns().stream().map(Index.Column::getColumn).collect(Collectors.toList());

            // check whether foreign key (of junction table) points to all columns of primary key in "From" table (and not just part of it)
            if(primaryKeyColumns.containsAll(relationship.getFromColumns())) {
                foreignTables++;
            }
        }

        return foreignTables == 2; // Junction table has foreign keys for two other tables (i.e. it points to primary keys of two other tables)
    }

}
