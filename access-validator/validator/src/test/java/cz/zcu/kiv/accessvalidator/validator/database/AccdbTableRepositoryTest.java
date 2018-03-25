package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import cz.zcu.kiv.accessvalidator.validator.BaseTestClass;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ColumnType;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ComparisonOperator;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.YesNoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author ike
 */
class AccdbTableRepositoryTest extends BaseTestClass {

    private AccdbTableRepository repository;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
        Accdb accdb = new Accdb(this.getTestDBFile());
        this.repository = accdb.getTableRepository();
    }

    @Test
    void ctor_BrokenDB_RuntimeExceptionThrown() throws IOException {
        Database db = Mockito.mock(Database.class);
        Mockito.doThrow(new IOException()).when(db).getTableNames();

        assertThrows(RuntimeException.class, () -> new AccdbTableRepository(db));
    }

    @Test
    void filterByName_BrokenDB_RuntimeExceptionThrown() throws IOException {
        Database db = Mockito.mock(Database.class);
        Mockito.when(db.getTableNames()).thenReturn(Collections.singleton("tbl"));
        Mockito.when(db.getTable(Mockito.anyString())).thenThrow(new IOException());
        Mockito.when(db.getRelationships(Mockito.any())).thenThrow(new IOException());

        AccdbTableRepository repository = new AccdbTableRepository(db);
        assertThrows(RuntimeException.class, () -> repository.filterByRowsCount(1, ComparisonOperator.GTE));
        assertThrows(RuntimeException.class, repository::filterMNJunctionTables);
    }

    @Test
    void getTables_UnfilteredTestDB_Found6() {
        assertEquals(6, this.repository.getTables().size());
    }

    @Test
    void filterByName_Tabulka1_Found1() {
        this.repository.filterByName("Tabulka1");
        assertEquals(1, this.repository.getTables().size());
    }

    @Test
    void filterByName_Tabulka2_Found0() {
        this.repository.filterByName("Tabulka2");
        assertEquals(0, this.repository.getTables().size());
    }


    @ParameterizedTest
    @MethodSource("getFilterByColumnCountArguments")
    void filterByColumnCount_SimpleDifferentRules_FoundExpectedNumberOfTables(int columnsCount, ComparisonOperator columnsCountOp, int expectedTables) {
        this.repository.filterByColumnCount(columnsCount, columnsCountOp);
        assertEquals(expectedTables, this.repository.getTables().size());
    }

    private static Stream<Arguments> getFilterByColumnCountArguments() {
        return Stream.of(
                Arguments.of(1, ComparisonOperator.GTE, 6),
                Arguments.of(8, ComparisonOperator.GTE, 1),
                Arguments.of(3, ComparisonOperator.LTE, 2),
                Arguments.of(5, ComparisonOperator.EQ, 1),
                Arguments.of(2, ComparisonOperator.EQ, 1),
                Arguments.of(0, ComparisonOperator.EQ, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getFilterByColumnCountArguments2")
    void filterByColumnCount_ComplexDifferentRules_FoundExpectedNumberOfTables(int columnsCount, ComparisonOperator columnsCountOp,
                                                                               String columnName, ColumnType columnType, YesNoType isPrimaryKey,
                                                                               int expectedTables) {
        this.repository.filterByColumnCount(columnsCount, columnsCountOp, columnName, columnType, isPrimaryKey);
        assertEquals(expectedTables, this.repository.getTables().size());
    }

    private static Stream<Arguments> getFilterByColumnCountArguments2() {
        return Stream.of(
                Arguments.of(0, ComparisonOperator.GTE, "", ColumnType._ANY, YesNoType._ANY, 6),
                Arguments.of(8, ComparisonOperator.GTE, "", ColumnType._ANY, YesNoType._ANY, 1),
                Arguments.of(3, ComparisonOperator.LTE, "", ColumnType._ANY, YesNoType._ANY, 2),
                Arguments.of(5, ComparisonOperator.EQ, "", ColumnType._ANY, YesNoType._ANY, 1),
                Arguments.of(2, ComparisonOperator.EQ, "", ColumnType._ANY, YesNoType._ANY, 1),
                Arguments.of(0, ComparisonOperator.EQ, "", ColumnType._ANY, YesNoType._ANY, 0),
                Arguments.of(1, ComparisonOperator.GTE, "ID", ColumnType._ANY, YesNoType._ANY, 2),
                Arguments.of(1, ComparisonOperator.GTE, "", ColumnType.AUTO_NUMBER, YesNoType._ANY, 4),
                Arguments.of(4, ComparisonOperator.GTE, "", ColumnType.TEXT, YesNoType._ANY, 2),
                Arguments.of(5, ComparisonOperator.GTE, "", ColumnType.TEXT, YesNoType._ANY, 0),
                Arguments.of(1, ComparisonOperator.GTE, "", ColumnType._ANY, YesNoType.YES, 6),
                Arguments.of(1, ComparisonOperator.GTE, "ID_doprava", ColumnType._ANY, YesNoType.YES, 1),
                Arguments.of(1, ComparisonOperator.GTE, "", ColumnType.CURRENCY, YesNoType._ANY, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("getFilterByColumnExistenceArguments")
    void filterByColumnExistence_DifferentRules_FoundExpectedNumberOfTables(String columnName, ColumnType columnType, YesNoType isPrimaryKey, int expectedTables) {
        this.repository.filterByColumnExistence(columnName, columnType, isPrimaryKey);
        assertEquals(expectedTables, this.repository.getTables().size());
    }

    private static Stream<Arguments> getFilterByColumnExistenceArguments() {
        return Stream.of(
                Arguments.of("", ColumnType._ANY, YesNoType._ANY, 6),
                Arguments.of("ID", ColumnType._ANY, YesNoType._ANY, 2),
                Arguments.of("", ColumnType._ANY, YesNoType.YES, 6),
                Arguments.of("ID_doprava", ColumnType._ANY, YesNoType.YES, 1),
                Arguments.of("", ColumnType.CURRENCY, YesNoType._ANY, 1),
                Arguments.of("", ColumnType.AUTO_NUMBER, YesNoType._ANY, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("getFilterByRowsCountArguments")
    void filterByRowsCount_DifferentRules_FoundExpectedNumberOfTables(int rowsCount, ComparisonOperator rowsCountOp, int expectedTables) {
        this.repository.filterByRowsCount(rowsCount, rowsCountOp);
        assertEquals(expectedTables, this.repository.getTables().size());
    }

    private static Stream<Arguments> getFilterByRowsCountArguments() {
        return Stream.of(
                Arguments.of(0, ComparisonOperator.GTE, 6),
                Arguments.of(4, ComparisonOperator.GTE, 4),
                Arguments.of(4, ComparisonOperator.LTE, 3),
                Arguments.of(4, ComparisonOperator.EQ, 1),
                Arguments.of(1, ComparisonOperator.EQ, 1),
                Arguments.of(0, ComparisonOperator.EQ, 1)
        );
    }



    @Test
    void filterMNJunctionTables__Found1() {
        this.repository.filterMNJunctionTables();
        assertEquals(1, this.repository.getTables().size());
    }


    @Test
    void MultipleFilters__FoundExpectedNumberOfTables() {
        this.repository.filterByColumnCount(1, ComparisonOperator.GTE, "", ColumnType.TEXT, YesNoType._ANY);
        assertEquals(5, this.repository.getTables().size());

        this.repository.filterByRowsCount(1, ComparisonOperator.GTE);
        assertEquals(5, this.repository.getTables().size());

        this.repository.filterByRowsCount(4, ComparisonOperator.GTE);
        assertEquals(4, this.repository.getTables().size());

        this.repository.filterByColumnCount(4, ComparisonOperator.GTE);
        assertEquals(3, this.repository.getTables().size());

        this.repository.filterByColumnExistence("Jmeno", ColumnType._ANY, YesNoType.NO);
        assertEquals(2, this.repository.getTables().size());

        this.repository.filterByColumnCount(0, ComparisonOperator.EQ, "tel_cislo", ColumnType._ANY, YesNoType._ANY);
        assertEquals(1, this.repository.getTables().size());

        this.repository.filterByName("Klient");
        assertEquals(1, this.repository.getTables().size());

        assertEquals("Klient", this.repository.getTables().toArray()[0]);
    }



}