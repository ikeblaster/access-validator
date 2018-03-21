package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.rules.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ike
 */
public class RulesRepository {

    private static final Collection<Rule> RULES = Arrays.asList(
            new GroupRule(),
            new ComplexRule(),
            new AllTablesHaveRowsRule(),
            new AllTablesHaveColumnsRule(),
            new ExistsTableByNameRule(),
            new CountTablesWithColumnRule(),
            new CountRelations11Rule(),
            new CountRelations1NRule(),
            new CountRelationsMNRule()
    );

    public static Collection<Rule> getAll() {
        return RULES;
    }

}
