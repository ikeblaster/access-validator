package cz.zcu.kiv.accessvalidator.configurator.components.details;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;

/**
 * Fixed implementation of non-public JavaFX class {@code org.controlsfx.property.editor.NumericField}.
 * Fixes problem with invalidation (internal editor initial value was always 0, regardless of class
 * initial value. When user changed value to 0, editor's internal value was still the same,
 * hence no invalidation event was fired.)
 * Supports only integers.
 *
 * @author Vojtech Kinkor
 */
class NumericField extends TextField {
    /**
     * Current value of field.
     */
    private final NumericField.IntegerValidator value;

    /**
     * Factory for GUI numeric editors.
     *
     * @param property Parent property.
     * @return Numeric editor.
     */
    public static AbstractPropertyEditor<Number, NumericField> createNumericEditor(PropertySheet.Item property) {
        return new AbstractPropertyEditor<Number, NumericField>(property, new NumericField()) {
            protected ObservableValue<Number> getObservableValue() {
                return this.getEditor().valueProperty();
            }

            public Number getValue() {
                return this.getEditor().valueProperty().getValue();
            }

            public void setValue(Number value) {
                this.getEditor().setText(value.toString());
            }
        };
    }

    /**
     * Fixed implementation of non-public JavaFX class {@code org.controlsfx.property.editor.NumericField}.
     */
    private NumericField() {
        this.value = new IntegerValidator(this);

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.value.setValue(this.value.toNumber(this.getText()));
            }
        });
    }

    /**
     * Gets observable value of field.
     *
     * @return Observable value of field.
     */
    public final ObservableValue<Number> valueProperty() {
        return this.value;
    }

    /**
     * Replaces a range of characters with the given text.
     *
     * @param start The starting index in the range, inclusive. This must be &gt;= 0 and &lt; the end.
     * @param end The ending index in the range, exclusive. This is one-past the last character to
     *            delete (consistent with the String manipulation methods). This must be &gt; the start,
     *            and &lt;= the length of the text.
     * @param text The text that is to replace the range. This must not be null.
     */
    @Override
    public void replaceText(int start, int end, String text) {
        if (this.replaceValid(start, end, text)) {
            super.replaceText(start, end, text);
        }
    }

    /**
     * Replaces the selection with the given replacement String. If there is
     * no selection, then the replacement text is simply inserted at the current
     * caret position. If there was a selection, then the selection is cleared
     * and the given replacement text inserted.
     */
    @Override
    public void replaceSelection(String text) {
        IndexRange range = this.getSelection();
        if (this.replaceValid(range.getStart(), range.getEnd(), text)) {
            super.replaceSelection(text);
        }
    }

    /**
     * Replaces a range of characters with the given text.
     *
     * @param start The starting index in the range, inclusive. This must be &gt;= 0 and &lt; the end.
     * @param end The ending index in the range, exclusive. This is one-past the last character to
     *            delete (consistent with the String manipulation methods). This must be &gt; the start,
     *            and &lt;= the length of the text.
     * @param fragment The text that is to replace the range. This must not be null.
     */
    private Boolean replaceValid(int start, int end, String fragment) {
        try {
            String newText = this.getText().substring(0, start) + fragment + this.getText().substring(end);
            if (newText.isEmpty()) {
                return true;
            } else {
                this.value.toNumber(newText);
                return true;
            }
        } catch (Throwable var5) {
            return false;
        }
    }

    /**
     * Validator of field values.
     */
    static class IntegerValidator extends SimpleIntegerProperty {
        /**
         * Parent field of value.
         */
        private NumericField field;

        /**
         * Validator of field values.
         * @param field Parent field of value.
         */
        IntegerValidator(NumericField field) {
            super(field, "value", Integer.MIN_VALUE);
            this.field = field;
        }

        /**
         * Receives invalidation notifications. Reset the field value to current value.
         */
        @Override
        protected void invalidated() {
            this.field.setText(Integer.toString(this.get()));
        }

        /**
         * Converts value from string to number.
         *
         * @param s String with number.
         * @return Number.
         */
        Integer toNumber(String s) {
            if (s != null && !s.trim().isEmpty()) {
                String d = s.trim();
                return Integer.valueOf(d);
            } else {
                return 0;
            }
        }
    }


}
