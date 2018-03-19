package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbRelationRepository;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.mockito.Mockito.mock;

/**
 * @author ike
 */
public class BaseRulesTestClass extends BaseTestClass {

    private static Accdb mockAccdb;

    @BeforeAll
    public static void initStaticMocks() {
        mockAccdb = mock(Accdb.class);
        AccdbTableRepository mockAccdbTableRepository = mock(AccdbTableRepository.class);
        AccdbRelationRepository mockAccdbRelationRepository = mock(AccdbRelationRepository.class);

        Mockito.when(mockAccdb.getTableRepository()).thenReturn(mockAccdbTableRepository);
        Mockito.when(mockAccdb.getRelationRepository()).thenReturn(mockAccdbRelationRepository);

        Mockito.when(mockAccdbTableRepository.getTables()).thenReturn(new HashSet<>());
        Mockito.when(mockAccdbRelationRepository.getRelations()).thenReturn(new HashSet<>());
    }

    public static Accdb getMockedAccdb() {
        return mockAccdb;
    }

}