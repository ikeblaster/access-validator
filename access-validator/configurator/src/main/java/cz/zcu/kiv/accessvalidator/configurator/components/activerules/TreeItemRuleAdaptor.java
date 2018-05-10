package cz.zcu.kiv.accessvalidator.configurator.components.activerules;

import cz.zcu.kiv.accessvalidator.configurator.components.details.PropertySheetRuleAdaptor;
import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * {@code Rule} adaptor for use as {@code TreeItem}.
 *
 * @author Vojtech Kinkor
 */
public class TreeItemRuleAdaptor extends TreeItem<Rule> {

    /**
     * Collection of items for {@code PropertySheet}, provides GUI for properties of the rule.
     */
    private final Collection<PropertySheetRuleAdaptor> propertySheetItems = new ArrayList<>();

    /**
     * {@code Rule} adaptor for use as {@code TreeItem}.
     *
     * @param rule The rule.
     */
    public TreeItemRuleAdaptor(Rule rule) {
        super(rule);

        for(Property<?> property : rule.getProperties()) {
            this.propertySheetItems.add(new PropertySheetRuleAdaptor(property));
        }

        if(this.isGroup()) {
            this.setExpanded(true);

            GroupRule groupRule = (GroupRule) rule;
            for(Rule child : groupRule.getRules()) {
                super.getChildren().add(new TreeItemRuleAdaptor(child));
            }
        }

        rule.onChange((observable) -> {
            TreeModificationEvent<Rule> event = new TreeModificationEvent<>(TreeItem.valueChangedEvent(), this);
            Event.fireEvent(this, event);
        });
    }


    /**
     * Not to be used for adding or removing children.
     * Use {@code addChild} and {@code removeChild} methods instead.
     *
     * @return A list that contains the child TreeItems belonging to the TreeItem.
     */
    @Override
    @Deprecated
    public ObservableList<TreeItem<Rule>> getChildren() {
        return super.getChildren();
    }

    /**
     * Adds a rule as child to the current rule.
     * Current rule must be {@code GroupRule}, action is ignored otherwise.
     *
     * @param rule Rule to be added as a child.
     */
    public void addChild(Rule rule) {
        if(this.isGroup()) {
            ((GroupRule) this.getValue()).getRules().add(rule); // add into GroupRule rules
            super.getChildren().add(new TreeItemRuleAdaptor(rule)); // add into tree
        }
    }

    /**
     * Removes a rule from children of current rule.
     * Current rule must be {@code GroupRule}, action is ignored otherwise.
     *
     * @param child Rule to be removed.
     */
    public void removeChild(TreeItem<Rule> child) {
        if(this.isGroup()) {
            ((GroupRule) this.getValue()).getRules().remove(child.getValue());
            super.getChildren().remove(child);
        }
    }

    /**
     * Checks whether current rule is a group rule, i.e. can contain other rules.
     *
     * @return {@code true} when current rule is a group rule, {@code false} otherwise.
     */
    public boolean isGroup() {
        return this.getValue() instanceof GroupRule;
    }

    /**
     * Gets a collection of items for {@code PropertySheet}, provides GUI for properties of the rule.
     * @return A collection of items for {@code PropertySheet}.
     */
    public Collection<PropertySheetRuleAdaptor> getPropertySheetItems() {
        return Collections.unmodifiableCollection(this.propertySheetItems);
    }

}
