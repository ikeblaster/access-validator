package cz.zcu.kiv.accessvalidator.validator.rules;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;

import java.util.*;

/**
 * Represents a parent rule for group of rules.
 *
 * @author Vojtech Kinkor
 */
public class GroupRule extends Rule {

    /**
     * Mode for checking the database.
     * {@code AND} - all children rules must be satisfied.
     * {@code OR} - at least one children rule must be satisfied.
     */
    public enum Mode {
        AND, OR
    }

    /**
     * Mode of the group rule. See enum {@code Mode}.
     */
    private ChoiceProperty<Mode> mode;

    /**
     * Group of nested rules.
     */
    private List<Rule> rules = new ArrayList<>();

    /**
     * Set of failed rules of last check.
     */
    private Set<Rule> failedRules = new LinkedHashSet<>();

    /**
     * Represents a parent rule for group of rules.
     */
    public GroupRule() {
        super();

        this.mode = this.addProperty(new ChoiceProperty<>(
                "mode",
                Mode.class, Mode.AND, Arrays.asList(Mode.values()),
                "Režim validace",
                "Podmínka pro splnění skupiny pravidel.\nAND = nutné splnit všechna pravidla.\nOR = nutné splnit alespoň jedno pravidlo.",
                "Režim validace"
        ));
    }

    /**
     * Gets collection of children rules.
     *
     * @return Collection of rules.
     */
    public List<Rule> getRules() {
        return this.rules;
    }

    /**
     * Checks database against the rule. Depends on set mode.
     * In {@code AND} mode checking is stopped immediately after first failed rule.
     * Similarily in {@code OR} mode checking is stopped after first successful rule.
     *
     * @param accdb Database.
     * @return {@code true} when database satisfies the rule, {@code false} otherwise.
     */
    @Override
    public boolean check(Accdb accdb) {
        this.failedRules.clear();

        if(this.rules.isEmpty()) {
            return true;
        }

        if(this.mode.getValue() == Mode.AND) {
            for (Rule rule : this.rules) {
                if(!rule.check(accdb)) {
                    this.addFailedRule(rule);
                    return false; // at least one is NOT successful
                }
            }
            return true;
        }
        else {
            for (Rule rule : this.rules) {
                if(rule.check(accdb)) {
                    return true; // at least one is successful
                }
                this.addFailedRule(rule);
            }
            return false;
        }
    }

    /**
     * Gets set of failed rules from last check.
     *
     * @return Set of failed rules.
     */
    public Set<Rule> getFailedRules() {
        return this.failedRules;
    }

    /**
     * Gets generic label for rule (i.e. label usable in any situation regardless of properties values).
     *
     * @return Generic label for rule.
     */
    @Override
    public String getGenericLabel() {
        return "Skupina pravidel";
    }

    /**
     * Gets rule label, usually shortly describing set properties.
     *
     * @return Label for rule.
     */
    @Override
    public String toString() {
        return "Skupina pravidel (" + this.mode.getValue() + ")";
    }

    /**
     * Compares this object to other object.
     *
     * @param o Other object.
     * @return {@code true} if the argument is equal to this object and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        GroupRule groupRule = (GroupRule) o;
        return Objects.equals(this.rules, groupRule.rules);
    }

    /**
     * Computes a hash value based on {@code toString()} and properties.
     *
     * @return A hash value of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.rules);
    }

    /**
     * Adds a failed rule into the set of failed rules.
     * For nested group rules all their failed rules are also added.
     *
     * @param rule Failed rule to be added into the set.
     */
    private void addFailedRule(Rule rule) {
        this.failedRules.add(rule);
        if(rule instanceof GroupRule) {
            GroupRule groupRule = (GroupRule) rule;
            this.failedRules.addAll(groupRule.getFailedRules());
        }
    }
}
