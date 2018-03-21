package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * @author ike
 */
public class AccdbValidator {

    private Accdb accdb;
    private Rule rule;

    public AccdbValidator(File file, Rule rule) throws IOException {
        this.accdb = new Accdb(file);
        this.rule = rule;
    }

    public boolean validate() {
        return this.rule.check(this.accdb);
    }

    public Set<Rule> getFailedRules() {
        if(this.rule instanceof GroupRule) {
            return ((GroupRule) this.rule).getFailedRules();
        }
        return Collections.emptySet();
    }


}
