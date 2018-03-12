package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.rules.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author ike
 */
public class RulesRepository {

    private static final Collection<Rule> RULES = Arrays.asList(
            new GroupRule(false),
            new ComplexRule(),
            new TableByNameExistsRule(),
            new TablesRowsCountRule(),
            new RelationMNExistsRule(),
            new Relation11ExistsRule()
    );

    public static Collection<Rule> getAll() {
        return RULES;
    }

}
