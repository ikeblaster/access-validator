package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vojtech Kinkor
 */
public class AccdbTest extends BaseTestClass {

    public static int FIND_SIMILARITIES_CHECKS_COUNT = 7;

    private Accdb accdb;
    private File file;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        this.file = this.getTestDBFile();
        this.accdb = new Accdb(this.file);
    }

    @Test
    void getTableRepository__NotNull() {
        assertNotNull(this.accdb.getTableRepository());
    }

    @Test
    void getRelationRepository__NotNull() {
        assertNotNull(this.accdb.getRelationRepository());
    }

    @Test
    void getQueryRepository__NotNull() {
        assertNotNull(this.accdb.getQueryRepository());
    }

    @Test
    void getFile__ReturnsParValue() {
        assertEquals(this.file, this.accdb.getFile());
    }

    @Test
    void findSimilarities_SameDB_FoundAllExpected() {
        Set<SimilarityElement> similarities = this.accdb.findSimilarityElements();
        assertEquals(FIND_SIMILARITIES_CHECKS_COUNT, similarities.size());
    }

    @Test
    void findSimilarities_RowNotFound_OK() throws IOException, NoSuchFieldException, IllegalAccessException {
        Accdb accdb2 = new Accdb(this.file);

        Field field = accdb2.getClass().getDeclaredField("db"); // fails here
        field.setAccessible(true);
        Database database = Mockito.spy((Database) field.get(accdb2));
        field.set(accdb2, database);

        Table tbl = database.getSystemTable("MSysNameMap");
        Mockito.when(database.getSystemTable("MSysObjects")).thenReturn(tbl);

        accdb2.findSimilarityElements();
    }

    @Test
    void findSimilarities_BrokenDB_ErrorMessagesPrintedToSystemErr() throws IOException, NoSuchFieldException, IllegalAccessException {
        Accdb accdb2 = new Accdb(this.file);

        Database brokenDB = Mockito.mock(Database.class);
        Mockito.when(brokenDB.getSummaryProperties()).thenThrow(IOException.class);
        Mockito.when(brokenDB.getSystemTable(Mockito.any())).thenThrow(IOException.class);

        Field field = accdb2.getClass().getDeclaredField("db"); // fails here
        field.setAccessible(true);
        field.set(accdb2, brokenDB);

        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        accdb2.findSimilarityElements();

        assertTrue(errContent.size() > 0);
    }

}