package cz.zcu.kiv.accessvalidator.configurator;

import cz.zcu.kiv.accessvalidator.configurator.rules.RuleProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;

import java.util.Optional;

/**
 * @author ike
 */
public class PropertySheetRuleAdaptor implements PropertySheet.Item {

    private RuleProperty ruleProperty;

    public PropertySheetRuleAdaptor(RuleProperty ruleProperty) {
        this.ruleProperty = ruleProperty;
    }

    @Override
    public Class<?> getType() {
        return this.ruleProperty.getValue().getClass();
    }

    @Override
    public String getCategory() {
        return this.ruleProperty.getCategory();
    }

    @Override
    public String getName() {
        return this.ruleProperty.getName();
    }

    @Override
    public String getDescription() {
        return this.ruleProperty.getDescription();
    }

    @Override
    public Object getValue() {
        return this.ruleProperty.getValue();
    }

    @Override
    public void setValue(Object value) {
        this.ruleProperty.setValue(value);
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }
}
