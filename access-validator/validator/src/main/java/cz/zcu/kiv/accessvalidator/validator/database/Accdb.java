package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represents a single ACCDB database, provides repositories
 * and interface for checking similarity to other databases.
 *
 * @author Vojtech Kinkor
 */
public class Accdb {

    /**
     * Internal objects (like queries embedded into forms) have this prefix.
     */
    static final String INTERNAL_OBJECTS_PREFIX = "~";

    /**
     * Layout is found in MSysObjects table, row with {@code Type=-32758}, column {@code LvExtra}.
     */
    private static final String RELATIONSHIPS_LAYOUT_RECORD_TYPE = "-32758";

    /**
     * File with database.
     */
    private File file;

    /**
     * Database instance.
     */
    private Database db;

    /**
     * Represents a single ACCDB database, provides repositories
     * and interface for checking similarity to other databases.
     *
     * @param file ACCDB database
     * @throws IOException
     */
    public Accdb(File file) throws IOException {
        this.file = file;
        this.db = DatabaseBuilder.open(file);
    }

    /**
     * Gets repository instance for searching tables in database.
     *
     * @return Table respository.
     */
    public AccdbTableRepository getTableRepository() {
        return new AccdbTableRepository(this.db);
    }

    /**
     * Gets repository instance for searching relationships in database.
     *
     * @return Relation respository.
     */
    public AccdbRelationRepository getRelationRepository() {
        return new AccdbRelationRepository(this.db);
    }

    /**
     * Gets repository instance for searching queries in database.
     *
     * @return Query respository.
     */
    public AccdbQueryRepository getQueryRepository() {
        return new AccdbQueryRepository(this.db);
    }

    /**
     * Gets current database file.
     *
     * @return Database file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Finds similarities with other database.
     *
     * @param other Other database.
     * @return Set of similarity elements.
     */
    public Set<SimilarityElement> findSimilarities(Accdb other) {
        Set<SimilarityElement> similarities = new HashSet<>();

        this.checkSimilarRelationsLayout(other, similarities);
        this.checkSimilarSummaryProperties(other, similarities);
        //this.checkSimilarDate(other, similarities, "Tables", "DateUpdate");
        this.checkSimilarDate(other, similarities, "MSysDb", "DateUpdate");
        this.checkSimilarDate(other, similarities, "Admin", "DateUpdate");
        //this.checkSimilarDate(other, similarities, "Tables", "DateCreate");
        this.checkSimilarDate(other, similarities, "MSysDb", "DateCreate");
        this.checkSimilarDate(other, similarities, "Admin", "DateCreate");

        return similarities;
    }

    /**
     * Checks similarity using summary properties.
     *
     * @param that Other database.
     * @param similarities Set of similarity elements for adding new similarity.
     */
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

    /**
     * Checks similarity using dates in MSysObjects table.
     *
     * @param that Other database.
     * @param similarities Set of similarity elements for adding new similarity.
     * @param mSysObjectName Name of object in MSysObjects (value in column {@code Name}).
     * @param dateColumn Name of column with date (usually {@code DateCreate} or {@code DateUpdate})
     */
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

    /**
     * Checks similarity using layout of relations designer.
     * Layout is found in MSysObjects table, row with {@code Type=-32758}, column {@code LvExtra}.
     *
     * @param that Other database.
     * @param similarities Set of similarity elements for adding new similarity.
     */
    private void checkSimilarRelationsLayout(Accdb that, Set<SimilarityElement> similarities) {
        try {
            Row row1 = CursorBuilder.findRow(this.db.getSystemTable("MSysObjects"), Collections.singletonMap("Type", RELATIONSHIPS_LAYOUT_RECORD_TYPE));
            Row row2 = CursorBuilder.findRow(that.db.getSystemTable("MSysObjects"), Collections.singletonMap("Type", RELATIONSHIPS_LAYOUT_RECORD_TYPE));

            if(row1 == null || row2 == null) {
                return;
            }

            byte[] data1 = row1.getBytes("LvExtra");
            byte[] data2 = row2.getBytes("LvExtra");

            /*
            First 68 Bytes are the Header
            This is followed by (NumWindows + 1) * 284 bytes per record
            Last record seems to be padding

            Each record:
                RelWinX1            long         4 B
                RelWinY1            long         4 B
                RelWinX2            long         4 B
                RelWinY2            long         4 B
                Junk                long         4 B
                WinName             string     128 B
                Junk                long         4 B
                WinNameMaster       string     128 B
                Junk                long         4 B

            source: http://www.lebans.com/saverelationshipview.htm
            */

            int offset = 68; // skip header

            if(data1 == null || data2 == null || data1.length < offset || data1.length != data2.length) {
                return;
            }

            for (int i = offset; i < data1.length; i++) {
                if (data1[i] != data2[i]) {
                    return;
                }
            }

            similarities.add(new SimilarityElement("Rozložení relací", data1, 100));
        }
        catch(Exception e) {
            System.err.println("Error when comparing " + this.file.getName() + " and " + that.file.getName());
            e.printStackTrace();
        }
    }

}
