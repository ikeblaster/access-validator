package cz.zcu.kiv.accessvalidator.validator.database;

import java.util.Objects;

/**
 * @author ike
 */
public class SimilarityElement {

    private String label;
    private Object value;

    public SimilarityElement(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.label + " (" + this.value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        SimilarityElement that = (SimilarityElement) o;
        return Objects.equals(this.label, that.label) && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.label, this.value);
    }
}
