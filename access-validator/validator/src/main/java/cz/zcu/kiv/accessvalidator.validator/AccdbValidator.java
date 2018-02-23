package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.rules.Rule;

import java.io.File;
import java.io.IOException;

/**
 * @author ike
 */
public class AccdbValidator {

    private Accdb accdb;

    public AccdbValidator(File file) throws IOException {
        this.accdb = new Accdb(file);
    }

    public boolean validate(Rule rule) {
        return rule.check(this.accdb);
    }


}
