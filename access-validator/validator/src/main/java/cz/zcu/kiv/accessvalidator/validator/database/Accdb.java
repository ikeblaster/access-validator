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
     * Finds similarities for comparing with other database.
     *
     * @return Set of similarity elements.
     */
    public Set<SimilarityElement> findSimilarityElements() {
        Set<SimilarityElement> similarities = new HashSet<>();

        this.checkSimilarRelationsLayout(similarities);
        this.checkSimilarSummaryProperties(similarities);
        this.checkSimilarDate(similarities, "MSysDb", "DateUpdate");
        this.checkSimilarDate(similarities, "Admin", "DateUpdate");
        this.checkSimilarDate(similarities, "MSysDb", "DateCreate");
        this.checkSimilarDate(similarities, "Admin", "DateCreate");

        return similarities;
    }

    /**
     * Checks similarity using summary properties.
     *
     * @param similarities Set of similarity elements for adding new similarity.
     */
    private void checkSimilarSummaryProperties(Set<SimilarityElement> similarities) {
        try {
            PropertyMap props = this.db.getSummaryProperties();

            for (PropertyMap.Property property : props) {

                String name = property.getName();
                String value = property.getValue().toString();

                if(name.equals("Title") && value.startsWith("Database")) {
                    continue;
                }
                if(name.equals("Author") && value.matches(".*((Windows)|(Uzivatel)|(Uživatel)).*")) {
                    continue;
                }
                if(name.equals("Company") && value.matches(".*((Univer)|(Microsoft)).*")) {
                    continue;
                }

                similarities.add(new SimilarityElement("Metadata databáze (" + name + "=" + value + ")"));
            }
        }
        catch(Exception e) {
            System.err.println("Error when getting summary properties for " + this.file.getName());
            e.printStackTrace();
        }
    }

    /**
     * Checks similarity using dates in MSysObjects table.
     *
     * @param similarities Set of similarity elements for adding new similarity.
     * @param mSysObjectName Name of object in MSysObjects (value in column {@code Name}).
     * @param dateColumn Name of column with date (usually {@code DateCreate} or {@code DateUpdate})
     */
    private void checkSimilarDate(Set<SimilarityElement> similarities, String mSysObjectName, String dateColumn) {
        try {
            Row row = CursorBuilder.findRow(this.db.getSystemTable("MSysObjects"), Collections.singletonMap("Name", mSysObjectName));

            if(row == null) {
                return;
            }

            Date date = row.getDate(dateColumn);
            similarities.add(new SimilarityElement("Datum (" + mSysObjectName + "." + dateColumn + " = " + date + ")"));
        }
        catch(Exception e) {
            System.err.println("Error when getting date for " + this.file.getName() + ", " + mSysObjectName + ", " + dateColumn);
            e.printStackTrace();
        }
    }

    /**
     * Checks similarity using layout of relations designer.
     * Layout is found in MSysObjects table, row with {@code Type=-32758}, column {@code LvExtra}.
     *
     * @param similarities Set of similarity elements for adding new similarity.
     */
    private void checkSimilarRelationsLayout(Set<SimilarityElement> similarities) {
        try {
            Row row = CursorBuilder.findRow(this.db.getSystemTable("MSysObjects"), Collections.singletonMap("Type", RELATIONSHIPS_LAYOUT_RECORD_TYPE));

            if(row == null) {
                return;
            }

            byte[] data1 = row.getBytes("LvExtra");

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
            byte[] dataWithoutPrefix = Arrays.copyOfRange(data1, offset, data1.length);

            similarities.add(new SimilarityElement("Rozložení relací", Arrays.hashCode(dataWithoutPrefix), 100));
        }
        catch(Exception e) {
            System.err.println("Error getting relationships layout for " + this.file.getName());
            e.printStackTrace();
        }
    }

}
