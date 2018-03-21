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

    public void filterByColumnCount(int columnCount, ComparisonOperator operator, String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTableColumnCountByCriteria(tableName, columnName, columnType, isPrimaryKey), columnCount));
    }

    public void filterByColumnName(String columnName) {
        this.tables.removeIf(tableName -> this.getTableColumnCountByCriteria(tableName, columnName, null, null) == 0);
    }

    public void filterByColumnType(ColumnType columnType) {
        this.tables.removeIf(tableName -> this.getTableColumnCountByCriteria(tableName, null, columnType, null) == 0);
    }

    public void filterByColumnNameAndType(String columnName, ColumnType columnType) {
        this.tables.removeIf(tableName -> this.getTableColumnCountByCriteria(tableName, columnName, columnType, null) == 0);
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


    private Table getTable(String tableName) {
        try {
            return this.db.getTable(tableName);
        } catch (Exception e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB changed
            throw new NullPointerException();
        }
    }

    private List<Relationship> getRelationships(String tableName) {
        try {
            return this.db.getRelationships(this.db.getTable(tableName));
        } catch (Exception e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB changed
            throw new NullPointerException();
        }
    }


    private int getTableColumnCountByCriteria(String tableName, String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        try {
            Table table = this.db.getTable(tableName);
            List<? extends Column> columns = new ArrayList<>(table.getColumns());

            if(isPrimaryKey != null && isPrimaryKey != YesNoType._ANY) {
                List<Column> primaryKeyColumns = table.getPrimaryKeyIndex().getColumns().stream().map(Index.Column::getColumn).collect(Collectors.toList());
                columns.removeIf(col -> (isPrimaryKey == YesNoType.YES) ^ primaryKeyColumns.contains(col));
            }
            if(columnName != null && !columnName.isEmpty()) {
                columns.removeIf(col -> !col.getName().equals(columnName));
            }
            if(columnType != null && columnType != ColumnType._ANY) {
                columns.removeIf(col -> !columnType.compare(col));
            }

            return columns.size();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
