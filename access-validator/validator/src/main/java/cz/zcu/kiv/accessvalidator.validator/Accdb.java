package cz.zcu.kiv.accessvalidator.validator;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.impl.TableImpl;
import cz.zcu.kiv.accessvalidator.validator.rules.ComparisonOperator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public class Accdb {

    public class Checker {

        private Database db;
        private Set<String> tables = Collections.emptySet();

        private Checker(Database db) {
            this.db = db;
            try {
                this.tables = new HashSet<>(db.getTableNames());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void filterByName(String name) {
            this.tables.retainAll(Collections.singleton(name));
        }

        public void filterByColumnCount(int columnCount, ComparisonOperator operator) {
            this.tables.removeIf(name -> !operator.compare(this.getTable(name).getColumnCount(), columnCount));
        }

        public void filterByColumnName(String columnName) {
            this.tables.removeIf(name -> !((TableImpl) this.getTable(name)).hasColumn(columnName));
        }

        public void filterByColumnType(String columnName) {
            // TODO: implement, ENUM do ComplexRule, zde porovnání
            //this.tables.removeIf(name -> !((TableImpl) this.getTable(name)).getColumn(columnName).getType().);
        }

        public Set<String> getFoundTables() {
            return this.tables;
        }

        private Table getTable(String name) {
            try {
                return this.db.getTable(name);
            } catch (IOException e) {
                throw new NullPointerException();
            }
        }

    }


    private Database db;

    public Accdb(File file) throws IOException {
        this.db = DatabaseBuilder.open(file);
    }

    public Checker getChecker() {
        return new Checker(this.db);
    }





}
