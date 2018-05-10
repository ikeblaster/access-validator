package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vojtech Kinkor
 */
class AccdbRelationRepositoryTest extends BaseTestClass {

    private AccdbRelationRepository repository;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        Accdb accdb = new Accdb(this.getTestDBFile());
        this.repository = accdb.getRelationRepository();
    }

    @Test
    void ctor_BrokenDB_RuntimeExceptionThrown() throws IOException {
        Database db = Mockito.mock(Database.class);
        Mockito.doThrow(new IOException()).when(db).getRelationships();

        assertThrows(RuntimeException.class, () -> new AccdbRelationRepository(db));
    }

    @Test
    void getRelations_UnfilteredTestDB_Found4() {
        assertEquals(4, this.repository.getRelations().size());
    }


    @Test
    void filter11Relations__Found1() {
        this.repository.filter11Relations();
        assertEquals(1, this.repository.getRelations().size());
    }

    @Test
    void filter1NRelations__Found2() {
        this.repository.filter1NRelations();
        assertEquals(3, this.repository.getRelations().size());
    }

}