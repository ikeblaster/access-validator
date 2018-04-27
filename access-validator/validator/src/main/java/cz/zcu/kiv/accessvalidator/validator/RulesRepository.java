package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.rules.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * Provides a repository of all accessible rules.
 *
 * @author ike
 */
public class RulesRepository {

    /**
     * List of all accessible rules.
     */
    private static final Collection<Rule> RULES = Arrays.asList(
            new GroupRule(),
            new ComplexRule(),
            new AllTablesHaveRowsRule(),
            new AllTablesHaveColumnsRule(),
            new ExistsTableByNameRule(),
            new CountTablesWithColumnRule(),
            new CountRelations11Rule(),
            new CountRelations1NRule(),
            new CountRelationsMNRule(),
            new CountQueriesRule()
    );

    /**
     * Gets a list of all accessible rules.
     *
     * @return List of accessible rules.
     */
    public static Collection<Rule> getAll() {
        return RULES;
    }

}
