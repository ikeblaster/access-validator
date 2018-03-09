package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.PropertyMap;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author ike
 */
public class Accdb {

    private File file;
    private Database db;

    public Accdb(File file) throws IOException {
        this.file = file;
        this.db = DatabaseBuilder.open(file);
    }

    public AccdbTableRepository getTableRepository() {
        return new AccdbTableRepository(this.db);
    }

    public File getFile() {
        return this.file;
    }


    public Set<SimilarityElement> findSimilarities(Accdb that) {
        Set<SimilarityElement> similarities = new HashSet<>();

        if(this.file.length() == that.file.length()) {
            similarities.add(new SimilarityElement("Stejná velikost souborů", this.file.length()));
        }

        try {
            PropertyMap props1 = this.db.getSummaryProperties();
            PropertyMap props2 = that.db.getSummaryProperties();
            List<String> sameProps = new ArrayList<>();

            props1.forEach(property -> {
                if(Objects.equals(property.getValue(), props2.get(property.getName()).getValue())) {
                    sameProps.add(property.getName() + "=" + property.getValue());
                }
            });

            if(sameProps.size() > 0) {
                similarities.add(new SimilarityElement("Stejné vlastnosti databáze", String.join("\n", sameProps)));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }


        return similarities;
    }

}
