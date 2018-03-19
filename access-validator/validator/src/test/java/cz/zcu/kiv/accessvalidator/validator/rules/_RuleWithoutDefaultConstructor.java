package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;


public class _RuleWithoutDefaultConstructor extends Rule {

    public _RuleWithoutDefaultConstructor(int unused) {
        unused++;
    }

    @Override
    public boolean check(Accdb accdb) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

}