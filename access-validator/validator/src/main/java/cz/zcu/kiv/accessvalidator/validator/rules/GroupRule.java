package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.annotations.Monitorable;
import javafx.beans.InvalidationListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ike
 */
public class GroupRule extends Rule implements Monitorable {

    public enum Mode {
        AND, OR
    }

    private ChoiceProperty<Mode> modeProperty;
    private List<Rule> rules = new ArrayList<>();
    private boolean modeInTitle = true;

    public GroupRule() {
        super();

        this.modeProperty = new ChoiceProperty<>(
                "mode",
                Mode.AND,
                Arrays.asList(Mode.values()),
                "Režim validace",
                "Podmínka pro splnění skupiny pravidel.\nAND = nutné splnit všechna pravidla.\nOR = nutné splnit alespoň jedno pravidlo.",
                "Režim validace");

        this.properties.add(this.modeProperty);
    }

    public GroupRule(boolean activeRule) {
        this();
        this.modeInTitle = activeRule;
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    @Override
    public void onChange(InvalidationListener listener) {
        this.modeProperty.onChange(listener);
    }

    @Override
    public boolean check(Accdb accdb) {

        if(this.modeProperty.getValue().equals(Mode.AND)) {
            for (Rule rule : this.rules) {
                if(!rule.check(accdb)) return false; // at least one is NOT successful
            }
            return true;
        }
        else {
            for (Rule rule : this.rules) {
                if(rule.check(accdb)) return true; // at least one is successful
            }
            return false;
        }

    }

    @Override
    public String toString() {
        return "Skupina pravidel" + (this.modeInTitle ? " (" + this.modeProperty.getValue().toString() + ")" : "");
    }

}
