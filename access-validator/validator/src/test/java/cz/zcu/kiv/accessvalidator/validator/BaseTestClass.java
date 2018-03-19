package cz.zcu.kiv.accessvalidator.validator;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

/**
 * @author ike
 */
public class BaseTestClass {

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

}
