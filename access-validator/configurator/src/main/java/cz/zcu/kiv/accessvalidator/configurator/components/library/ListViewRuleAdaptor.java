package cz.zcu.kiv.accessvalidator.configurator.components.library;

import cz.zcu.kiv.accessvalidator.validator.rules.Rule;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * {@code Rule} adaptor for use as and item in {@code ListView}.
 *
 * @author Vojtech Kinkor
 */
public class ListViewRuleAdaptor {

    /**
     * Associated rule.
     */
    private final Rule rule;

    /**
     * Maps a collection of rules into a collection of adaptors.
     *
     * @param rules Source collection of rules.
     * @return Collection of rules wrapped in adaptors.
     */
    public static Collection<ListViewRuleAdaptor> wrapAll(Collection<Rule> rules) {
        return rules.stream().map(ListViewRuleAdaptor::new).collect(Collectors.toList());
    }

    /**
     * {@code Rule} adaptor for use as and item in {@code ListView}.
     *
     * @param rule Associated rule.
     */
    public ListViewRuleAdaptor(Rule rule) {
        this.rule = rule;
    }

    /**
     * Gets associated rule.
     *
     * @return Rule associated with
     */
    public Rule getRule() {
        return this.rule;
    }

    /**
     * Gets generic label of the rule.
     *
     * @return Generic label of the rule.
     */
    @Override
    public String toString() {
        return this.rule.getGenericLabel();
    }
}
