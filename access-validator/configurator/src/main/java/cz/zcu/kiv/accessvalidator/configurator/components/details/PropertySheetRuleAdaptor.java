package cz.zcu.kiv.accessvalidator.configurator.components.details;

import cz.zcu.kiv.accessvalidator.validator.rules.properties.ChoiceProperty;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.Property;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.Editors;
import org.controlsfx.property.editor.PropertyEditor;

import java.util.Optional;

/**
 * {@code rules.properties.Property} adaptor for use as {@code PropertySheet.Item}.
 *
 * @author Vojtech Kinkor
 */
public class PropertySheetRuleAdaptor implements PropertySheet.Item {

    /**
     * Associated property of rule.
     */
    private final Property<?> property;

    /**
     * {@code rules.properties.Property} adaptor for use as {@code PropertySheet.Item}.
     *
     * @param property Associated property of rule.
     */
    public PropertySheetRuleAdaptor(Property<?> property) {
        this.property = property;
    }

    /**
     * Gets the class type of the property.
     *
     * @return Class type of the property.
     */
    @Override
    public Class<?> getType() {
        return this.property.getValue().getClass();
    }

    /**
     * Gets category of the property.
     *
     * @return Category of the property.
     */
    @Override
    public String getCategory() {
        return this.property.getCategory();
    }

    /**
     * Gets name of the property.
     *
     * @return Name of the property.
     */
    @Override
    public String getName() {
        return this.property.getName();
    }

    /**
     * Gets description of the property. Will be shown in tooltip.
     *
     * @return Description of the property.
     */
    @Override
    public String getDescription() {
        return this.property.getDescription();
    }

    /**
     * Gets value of the property.
     *
     * @return Value of the property.
     */
    @Override
    public Object getValue() {
        return this.property.getValue();
    }

    /**
     * Sets new value to the property.
     * @param value New value.
     */
    @Override
    public void setValue(Object value) {
        try {
            this.property.setRawValue(value);
        } catch(RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deprecated, return {@code Optional.empty()}.
     *
     * @return {@code Optional.empty()}.
     */
    @Override
    @Deprecated
    public Optional<ObservableValue<?>> getObservableValue() {
        return Optional.empty();
    }

    /**
     * Gets property editor for UI.
     *
     * @return Property editor.
     */
    public PropertyEditor<?> getPropertyEditor() {
        if(this.property instanceof ChoiceProperty) {
            ChoiceProperty<?> property = (ChoiceProperty<?>) this.property;
            return Editors.createChoiceEditor(this, property.getChoices());
        }

        if (this.getValue() instanceof Boolean) {
            return Editors.createCheckEditor(this);
        }

        if (this.getValue() instanceof Integer) {
            return NumericField.createNumericEditor(this);
        }

        return Editors.createTextEditor(this);
    }

}
