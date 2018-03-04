package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @author ike
 */
public class Accdb {

    private Database db;

    public Accdb(File file) throws IOException {
        this.db = DatabaseBuilder.open(file);
    }

    public AccdbTableRepository getTableRepository() {
        return new AccdbTableRepository(this.db);
    }

}
