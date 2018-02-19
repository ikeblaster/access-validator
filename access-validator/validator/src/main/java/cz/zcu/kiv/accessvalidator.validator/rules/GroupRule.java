package cz.zcu.kiv.accessvalidator.validator.rules;

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

    private ChoiceProperty modeProperty;
    private List<Rule> rules = new ArrayList<>();
    private boolean modeInTitle = true;

    public enum Mode {
        AND, OR
    }

    public GroupRule() {
        super();

        this.modeProperty = new ChoiceProperty(
                "mode",
                Mode.AND,
                Arrays.asList((Object[]) Mode.values()),
                "Režim validace",
                "Podmínka pro splnění skupiny pravidel.\nAND = nutné splnit všechna pravidla.\nOR = nutné splnit alespoň jedno pravidlo.",
                "Režim validace");

        this.properties.add(modeProperty);
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
    public String toString() {
        return "Skupina pravidel" + (this.modeInTitle ? " (" + this.modeProperty.getValue().toString() + ")" : "");
    }

}
