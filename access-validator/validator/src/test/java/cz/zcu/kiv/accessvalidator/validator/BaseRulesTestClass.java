package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbQueryRepository;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbRelationRepository;
import cz.zcu.kiv.accessvalidator.validator.database.AccdbTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.HashSet;

/**
 * @author Vojtech Kinkor
 */
public class BaseRulesTestClass extends BaseTestClass {

    private Accdb mockAccdb;

    @BeforeEach
    @Override
    public void initMocks() {
        super.initMocks();

        this.mockAccdb = Mockito.mock(Accdb.class);
        AccdbTableRepository mockAccdbTableRepository = Mockito.mock(AccdbTableRepository.class);
        AccdbRelationRepository mockAccdbRelationRepository = Mockito.mock(AccdbRelationRepository.class);
        AccdbQueryRepository mockAccdbQueryRepository = Mockito.mock(AccdbQueryRepository.class);

        Mockito.when(this.mockAccdb.getTableRepository()).thenReturn(mockAccdbTableRepository);
        Mockito.when(this.mockAccdb.getRelationRepository()).thenReturn(mockAccdbRelationRepository);
        Mockito.when(this.mockAccdb.getQueryRepository()).thenReturn(mockAccdbQueryRepository);

        Mockito.when(mockAccdbTableRepository.getTables()).thenReturn(new HashSet<>());
        Mockito.when(mockAccdbRelationRepository.getRelations()).thenReturn(new HashSet<>());
    }

    public Accdb getMockedAccdb() {
        return this.mockAccdb;
    }

}