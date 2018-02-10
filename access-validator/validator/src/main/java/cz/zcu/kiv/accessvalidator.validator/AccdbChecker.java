package cz.zcu.kiv.accessvalidator.validator;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author ike
 */
public class AccdbChecker {

    Database db;

    public AccdbChecker(File file) throws IOException {
        this.db = DatabaseBuilder.open(file);
    }

    public boolean hasTable(String table) throws IOException {
        return this.db.getTable(table) != null;
    }

    public int tableColumnCount(String table) throws IOException {
        return this.db.getTable(table).getColumnCount();
    }

    public boolean tableHasColumn(String table, String column) throws IOException {
        try {
            return this.hasTable(table) && this.db.getTable(table).getColumn(column) != null;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }




}
