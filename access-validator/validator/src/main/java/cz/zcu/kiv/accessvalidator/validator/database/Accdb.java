package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.*;

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

    public AccdbRelationRepository getRelationRepository() {
        return new AccdbRelationRepository(this.db);
    }

    public AccdbQueryRepository getQueryRepository() {
        return new AccdbQueryRepository(this.db);
    }


    public File getFile() {
        return this.file;
    }


    public Set<SimilarityElement> findSimilarities(Accdb that) {
        Set<SimilarityElement> similarities = new HashSet<>();

        this.checkSimilarRelationsLayout(that, similarities);
        this.checkSimilarSummaryProperties(that, similarities);
        this.checkSimilarDate(that, similarities, "Tables", "DateUpdate");
        this.checkSimilarDate(that, similarities, "MSysDb", "DateUpdate");
        this.checkSimilarDate(that, similarities, "Admin", "DateUpdate");
        //this.checkSimilarDate(that, similarities, "Tables", "DateCreate");
        //this.checkSimilarDate(that, similarities, "MSysDb", "DateCreate");
        //this.checkSimilarDate(that, similarities, "Admin", "DateCreate");
        //this.checkSimilarFilesize(that, similarities);

        return similarities;
    }

    private void checkSimilarFilesize(Accdb that, Set<SimilarityElement> similarities) {
        if(this.file.length() == that.file.length() && (this.file.length() % 16000) != 0) {
            similarities.add(new SimilarityElement("Velikost souborů (" + this.file.length() + " B)"));
        }
    }

    private void checkSimilarSummaryProperties(Accdb that, Set<SimilarityElement> similarities) {
        try {
            PropertyMap props1 = this.db.getSummaryProperties();
            PropertyMap props2 = that.db.getSummaryProperties();
            List<String> sameProps = new ArrayList<>();

            props1.forEach(property -> {
                String propertyName = property.getName();

                if(propertyName.equals("Title") && property.getValue().toString().startsWith("Database")) {
                    return;
                }
                if(propertyName.equals("Author") && property.getValue().toString().contains("Windows")) {
                    return;
                }
                if(propertyName.equals("Company") && property.getValue().toString().contains("Univer")) {
                    return;
                }

                PropertyMap.Property otherProperty = props2.get(propertyName);

                if(otherProperty != null && Objects.equals(property.getValue(), otherProperty.getValue())) {
                    sameProps.add(propertyName + "=" + property.getValue());
                }
            });

            if(sameProps.size() > 0) {
                similarities.add(new SimilarityElement("Metadata databáze (" + String.join("; ", sameProps) + ")"));
            }
        }
        catch(Exception e) {
            System.err.println("Error when comparing " + this.file.getName() + " and " + that.file.getName());
            e.printStackTrace();
        }
    }

    private void checkSimilarDate(Accdb that, Set<SimilarityElement> similarities, String mSysObjectName, String dateColumn) {
        try {
            Row row1 = CursorBuilder.findRow(this.db.getSystemTable("MSysObjects"), Collections.singletonMap("Name", mSysObjectName));
            Row row2 = CursorBuilder.findRow(that.db.getSystemTable("MSysObjects"), Collections.singletonMap("Name", mSysObjectName));

            if(row1 == null || row2 == null) {
                return;
            }

            Date dateCreate1 = row1.getDate(dateColumn);
            Date dateCreate2 = row2.getDate(dateColumn);

            if(dateCreate1.equals(dateCreate2)) {
                similarities.add(new SimilarityElement("Datum (" + mSysObjectName + "." + dateColumn + " = " + dateCreate1 + ")"));
            }
        }
        catch(Exception e) {
            System.err.println("Error when comparing " + this.file.getName() + " and " + that.file.getName());
            e.printStackTrace();
        }
    }

    private void checkSimilarRelationsLayout(Accdb that, Set<SimilarityElement> similarities) {
        try {
            Row row1 = CursorBuilder.findRow(this.db.getSystemTable("MSysObjects"), Collections.singletonMap("Name", "Admin"));
            Row row2 = CursorBuilder.findRow(that.db.getSystemTable("MSysObjects"), Collections.singletonMap("Name", "Admin"));

            if(row1 == null || row2 == null) {
                return;
            }

            byte[] data1 = row1.getBytes("LvExtra");
            byte[] data2 = row2.getBytes("LvExtra");

            int offset = 64; // skip first 64 bytes

            if(data1 == null || data2 == null || data1.length < offset || data1.length != data2.length) {
                return;
            }

            for (int i = offset; i < data1.length; i++) {
                if (data1[i] != data2[i]) {
                    return;
                }
            }

            similarities.add(new SimilarityElement("Rozložení relací", data1));
        }
        catch(Exception e) {
            System.err.println("Error when comparing " + this.file.getName() + " and " + that.file.getName());
            e.printStackTrace();
        }
    }

}
