package cz.zcu.kiv.accessvalidator.validator.database;

import java.io.File;
import java.util.*;

/**
 * @author ike
 */
public class SimilarityElement {

    private String label;
    private Object value;

    private Set<File> files = new HashSet<>();

    public SimilarityElement(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public SimilarityElement(String label) {
        this.label = label;
        this.value = null;
    }

    public String getLabel() {
        return this.label;
    }

    public Object getValue() {
        return this.value;
    }

    public Collection<File> getFiles() {
        return this.files;
    }

    public void add(File file) {
        this.files.add(file);
    }

    @Override
    public String toString() {
        return this.label;
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
