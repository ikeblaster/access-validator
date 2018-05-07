package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.*;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ColumnType;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.YesNoType;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a repository for searching tables in ACCDB database.
 *
 * @author ike
 */
public class AccdbTableRepository {

    /**
     * Database instance.
     */
    private Database db;

    /**
     * Current set of tables.
     */
    private Set<String> tables;

    /**
     * Represents a repository for searching tables in ACCDB database.
     *
     * @param db Database instance.
     */
    AccdbTableRepository(Database db) {
        this.db = db;
        try {
            this.tables = new HashSet<>(db.getTableNames());
            this.tables.removeIf(table -> table.startsWith("~"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters current set of tables by name (leaves only exactly matching table).
     *
     * @param tableName Name of table.
     */
    public void filterByName(String tableName) {
        this.tables.retainAll(Collections.singleton(tableName));
    }

    /**
     * Filters current set of tables by number of columns they have.
     *
     * @param columnCount Desired number of columns.
     * @param operator Operator for comparing found number of columns.
     */
    public void filterByColumnCount(int columnCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getColumnCount(), columnCount));
    }

    /**
     * Filters current set of tables by number of columns they have.
     *
     * @param columnCount Desired number of columns.
     * @param operator Operator for comparing found number of columns.
     * @param columnName Filter found columns by name (exact match).
     * @param columnType Filter found columns by type.
     * @param isPrimaryKey Filter found columns by being or not being a primary key.
     */
    public void filterByColumnCount(int columnCount, ComparisonOperator operator, String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTableColumnCountByCriteria(tableName, columnName, columnType, isPrimaryKey), columnCount));
    }

    /**
     * Filters current set of tables by existence of column searched for by criteria.
     *
     * @param columnName Filter found columns by name (exact match).
     * @param columnType Filter found columns by type.
     * @param isPrimaryKey Filter found columns by being or not being a primary key.
     */
    public void filterByColumnExistence(String columnName, ColumnType columnType, YesNoType isPrimaryKey) {
        this.tables.removeIf(tableName -> this.getTableColumnCountByCriteria(tableName, columnName, columnType, isPrimaryKey) == 0);
    }

    /**
     * Filters current set of tables by row count.
     *
     * @param rowCount Desired number of rows.
     * @param operator Operator for comparing found number of rows.
     */
    public void filterByRowCount(int rowCount, ComparisonOperator operator) {
        this.tables.removeIf(tableName -> !operator.compare(this.getTable(tableName).getRowCount(), rowCount));
    }

    /**
     * Filters out anything else other than M:N junction tables.
     */
    public void filterMNJunctionTables() {
        this.tables.removeIf(tableName -> !this.isMNJunctionTable(tableName));
    }

    /**
     * Gets filtered set of relationships.
     *
     * @return Set of found relationships.
     */
    public Set<String> getTables() {
        return this.tables;
    }

    /**
     * Gets single table by name.
     *
     * @param tableName Name of table.
     * @throws RuntimeException for IO errors.
     * @return Table or {@code null} when not found.
     */
    private Table getTable(String tableName) {
        try {
            return this.db.getTable(tableName);
        } catch (IOException e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB file changed
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets collection of relationships for table defined by name.
     *
     * @param tableName Name of table.
     * @throws RuntimeException for IO errors.
     * @return Collection of relationships.
     */
    private List<Relationship> getRelationships(String tableName) {
        try {
            return this.db.getRelationships(this.db.getTable(tableName));
        } catch (IOException e) {
            // this means that we can't find a table in DB anymore, although we found it at first; maybe DB file changed
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets column count filtered by criteria for single table.
     *
     * @param tableName Name of table.
     * @param columnName Filter found columns by name (exact match).
     * @param columnType Filter found columns by type.
     * @param isPrimaryKey Filter found columns by being or not being a primary key.
     * @return Column count.
     */
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
            try {
                List<Column> primaryKeyColumns = table.getPrimaryKeyIndex().getColumns().stream().map(Index.Column::getColumn).collect(Collectors.toList());
                columns.removeIf(col -> (isPrimaryKey == YesNoType.YES) ^ primaryKeyColumns.contains(col)); // either we are looking for primary key and col isn't one... or the exact opposite
            } catch (IllegalArgumentException e) {
                // no primary key index on this table found and we are looking explicitely for primary keys
                if(isPrimaryKey == YesNoType.YES) {
                    columns.clear();
                }
            }
        }

        return columns.size();
    }

    /**
     * Checks whether the table is M:N junction table.
     *
     * @param tableName Name of table.
     * @return True, when table is M:N junction table.
     */
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
