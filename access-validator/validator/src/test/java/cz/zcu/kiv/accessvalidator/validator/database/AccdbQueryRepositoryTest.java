package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.QueryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ike
 */
class AccdbQueryRepositoryTest extends BaseTestClass {

    private AccdbQueryRepository repository;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        Accdb accdb = new Accdb(this.getTestDBFile());
        this.repository = accdb.getQueryRepository();
    }

    @Test
    void ctor_BrokenDB_RuntimeExceptionThrown() throws IOException {
        Database db = Mockito.mock(Database.class);
        Mockito.doThrow(new IOException()).when(db).getQueries();

        assertThrows(RuntimeException.class, () -> new AccdbQueryRepository(db));
    }

    @Test
    void getQueries_UnfilteredTestDB_Found4() {
        assertEquals(4, this.repository.getQueries().size());
    }

    @ParameterizedTest
    @EnumSource(value = QueryType.class, names = {"SELECT", "UPDATE", "APPEND", "DELETE"})
    void filterByType_FilteredQueriesInTestDB_Found1(QueryType type) {
        this.repository.filterByType(type);
        assertEquals(1, this.repository.getQueries().size());
    }


}