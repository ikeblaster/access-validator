package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.annotations.Monitorable;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author ike
 */
public class GroupRule extends Rule implements Monitorable {

    public enum Mode {
        AND, OR
    }

    private ChoiceProperty<Mode> mode;
    private List<Rule> rules = new ArrayList<>();

    public GroupRule() {
        super();

        this.mode = this.addProperty(new ChoiceProperty<>(
                Mode.class,
                "mode",
                Mode.AND,
                Arrays.asList(Mode.values()),
                "Režim validace",
                "Podmínka pro splnění skupiny pravidel.\nAND = nutné splnit všechna pravidla.\nOR = nutné splnit alespoň jedno pravidlo.",
                "Režim validace"
        ));
    }

    public List<Rule> getRules() {
        return this.rules;
    }

    @Override
    public boolean check(Accdb accdb) {

        if(this.mode.getValue() == Mode.AND) {
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
    public String getGenericLabel() {
        return "Skupina pravidel";
    }

    @Override
    public String toString() {
        return "Skupina pravidel (" + this.mode.getValue() + ")";
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        GroupRule groupRule = (GroupRule) o;
        return Objects.equals(this.rules, groupRule.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.rules);
    }
}
