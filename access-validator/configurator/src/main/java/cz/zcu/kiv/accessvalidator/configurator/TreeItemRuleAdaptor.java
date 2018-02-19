package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.validator.rules.GroupRule;
import cz.zcu.kiv.accessvalidator.validator.rules.Rule;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author ike
 */
public class TreeItemRuleAdaptor extends TreeItem<Rule> {

    private Collection<PropertySheetRuleAdaptor> propertySheetItems = new ArrayList<>();

    public TreeItemRuleAdaptor(Rule rule) {
        super(rule);

        for(Property property : rule.getProperties()) {
            PropertySheetRuleAdaptor ruleAdaptor = new PropertySheetRuleAdaptor(property);
            this.propertySheetItems.add(ruleAdaptor);
        }

        if(this.isGroup()) {
            this.setExpanded(true);

            GroupRule groupRule = (GroupRule) rule;
            for(Rule child : groupRule.getRules()) {
                super.getChildren().add(new TreeItemRuleAdaptor(child));
            }
        }

    }

    @Override
    @Deprecated
    public ObservableList<TreeItem<Rule>> getChildren() {
        return super.getChildren();
    }

    public void addChild(Rule rule) {
        if(this.isGroup()) {
            ((GroupRule) this.getValue()).getRules().add(rule); // add into GroupRule rules
            super.getChildren().add(new TreeItemRuleAdaptor(rule)); // add into tree
        }
    }

    public boolean isGroup() {
        return this.getValue() instanceof GroupRule;
    }

    public Collection<PropertySheetRuleAdaptor> getPropertySheetItems() {
        return Collections.unmodifiableCollection(this.propertySheetItems);
    }

}
