package cz.zcu.kiv.accessvalidator.components.details;

import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;

/**
 * @author ike
 */
class NumericField extends TextField {
    private final NumericField.NumericValidator<? extends Number> value;

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

    private NumericField() {
        this.value = new IntegerValidator(this);

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.value.setValue(this.value.toNumber(this.getText()));
            }
        });
    }

    public final ObservableValue<Number> valueProperty() {
        return this.value;
    }

    public void replaceText(int start, int end, String text) {
        if (this.replaceValid(start, end, text)) {
            super.replaceText(start, end, text);
        }
    }

    public void replaceSelection(String text) {
        IndexRange range = this.getSelection();
        if (this.replaceValid(range.getStart(), range.getEnd(), text)) {
            super.replaceSelection(text);
        }
    }

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

    static class IntegerValidator extends SimpleIntegerProperty implements NumericField.NumericValidator<Integer> {
        private NumericField field;

        public IntegerValidator(NumericField field) {
            super(field, "value", Integer.MIN_VALUE);
            this.field = field;
        }

        protected void invalidated() {
            this.field.setText(Integer.toString(this.get()));
        }

        public Integer toNumber(String s) {
            if (s != null && !s.trim().isEmpty()) {
                String d = s.trim();
                return new Integer(d);
            } else {
                return 0;
            }
        }
    }

    private interface NumericValidator<T extends Number> extends NumberExpression {
        void setValue(Number var1);

        T toNumber(String var1);
    }
}
