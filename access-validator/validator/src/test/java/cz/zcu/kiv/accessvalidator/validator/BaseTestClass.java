package cz.zcu.kiv.accessvalidator.validator;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Vojtech Kinkor
 */
public class BaseTestClass {

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    protected File getTestDBFile() throws URISyntaxException {
        return new File(BaseTestClass.class.getClassLoader().getResource("testdb.accdb").toURI());
    }

}
