package cz.zcu.kiv.accessvalidator.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ike
 */
class RulesRepositoryTest extends BaseTestClass {

    @Test
    void getAll__IsNotEmpty() {
        assertTrue(!RulesRepository.getAll().isEmpty());
    }

}