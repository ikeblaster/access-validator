package cz.zcu.kiv.accessvalidator.components.details;

import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

/**
 * @author ike
 */
public class PropertySheetRuleAdaptor implements PropertySheet.Item {

    private Property property;

    public PropertySheetRuleAdaptor(Property property) {
        this.property = property;
    }

    @Override
    public Class<?> getType() {
        return this.property.getValue().getClass();
    }

    @Override
    public String getCategory() {
        return this.property.getCategory();
    }

    @Override
    public String getName() {
        return this.property.getName();
    }

    @Override
    public String getDescription() {
        return this.property.getDescription();
    }

    @Override
    public Object getValue() {
        return this.property.getValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object value) {
        try {
            this.property.setValue(value);
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Deprecated
    public Optional<ObservableValue<?>> getObservableValue() {
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public PropertyEditor<?> getPropertyEditor() {
        if(this.property instanceof ChoiceProperty) {
            ChoiceProperty property = (ChoiceProperty) this.property;
            return Editors.createChoiceEditor(this, property.getChoices());
        }

        if (this.getValue() instanceof Boolean) {
            return Editors.createCheckEditor(this);
        }

        if (this.getValue() instanceof Integer) {
            return Editors.createNumericEditor(this);
        }

        return Editors.createTextEditor(this);
    }

}
