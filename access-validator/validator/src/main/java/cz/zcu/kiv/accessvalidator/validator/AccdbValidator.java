package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Provides a tool for validating a single database file against a rule.
 *
 * @author Vojtech Kinkor
 */
public class AccdbValidator {

    /**
     * Checked database.
     */
    private Accdb accdb;

    /**
     * Rule for checking the database against.
     */
    private Rule rule;

    /**
     * Provides a tool for validating a single database file against a rule.
     *
     * @param file ACCDB database file (MDB file should also work).
     * @param rule Rule the database will be checked against.
     * @throws IOException
     */
    public AccdbValidator(File file, Rule rule) throws IOException {
        this.accdb = new Accdb(file);
        this.rule = rule;
    }

    /**
     * Validates the database against the rule (i.e. checks whether the rule is satisfied by the database).
     *
     * @return {@code true} when the database satisfied the rule, {@code false} otherwise.
     */
    public boolean validate() {
        return this.rule.check(this.accdb);
    }

    /**
     * Gets set of failed rules from last validation.
     *
     * @return Set of failed rules.
     */
    public Set<Rule> getFailedRules() {
        if(this.rule instanceof GroupRule) {
            return ((GroupRule) this.rule).getFailedRules();
        }
        return Collections.emptySet();
    }


}
