package cz.zcu.kiv.accessvalidator.components.library;

import cz.zcu.kiv.accessvalidator.validator.rules.Rule;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author ike
 */
public class ListViewRuleAdaptor {

    private final Rule rule;

    public static Collection<ListViewRuleAdaptor> wrapAll(Collection<Rule> rules) {
        return rules.stream().map(rule -> new ListViewRuleAdaptor(rule)).collect(Collectors.toList());
    }

    public ListViewRuleAdaptor(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return this.rule;
    }

    @Override
    public String toString() {
        return this.rule.getGenericLabel();
    }
}
