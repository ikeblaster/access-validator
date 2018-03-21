package cz.zcu.kiv.accessvalidator.validator.database;

import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ike
 */
class AccdbTest extends BaseTestClass {

    private Accdb accdb;
    private File db;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        this.db = new File(this.getClass().getClassLoader().getResource("db_invalid.accdb").toURI());
        this.accdb = new Accdb(this.db);
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
    void getFile__ReturnsParValue() {
        assertEquals(this.db, this.accdb.getFile());
    }

    @Test
    void findSimilarities_SameDB_Found() throws IOException {
        Accdb accdb2 = new Accdb(this.db);
        Set<SimilarityElement> similarities = this.accdb.findSimilarities(accdb2);
        assertTrue(similarities.size() > 0);
    }

}