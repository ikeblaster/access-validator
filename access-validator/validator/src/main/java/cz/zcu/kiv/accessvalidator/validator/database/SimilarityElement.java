package cz.zcu.kiv.accessvalidator.validator.database;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Single element of similarity which is shared across files.
 *
 * @author Vojtech Kinkor
 */
public class SimilarityElement {

    /**
     * Short description of similarity.
     */
    private String label;

    /**
     * Value used in comparing with other files.
     */
    private Object value;

    /**
     * Collection of files with this similarity.
     */
    private Set<File> files = new HashSet<>();

    /**
     * Severity of similarity in range 0-100.
     */
    private int severity = 1;

    /**
     * Single element of similarity which is shared across files.
     *
     * @param label Short description of similarity.
     * @param value Value used in comparing with other files.
     */
    public SimilarityElement(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    /**
     * Single element of similarity which is shared across files.
     *
     * @param label Short description of similarity.
     * @param value Value used in comparing with other files.
     * @param severity Severity of similarity.
     */
    public SimilarityElement(String label, Object value, int severity) throws Exception {
        if(severity < 0 || severity > 100) {
            throw new Exception("Severity must be between 0 and 100 (inclusive).");
        }

        this.label = label;
        this.value = value;
        this.severity = severity;
    }

    /**
     * Single element of similarity which is shared across files.
     *
     * @param label Short description of similarity.
     */
    public SimilarityElement(String label) {
        this.label = label;
        this.value = null;
    }

    /**
     * Gets label - short description of similarity.
     *
     * @return Label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Gets value used in comparing with other files.
     *
     * @return Value of similarity.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Gets severity of similarity.
     *
     * @return Severity of similarity.
     */
    public int getSeverity() {
        return this.severity;
    }

    /**
     * Collection of files having this similarity.
     *
     * @return Collection of files.
     */
    public Collection<File> getFiles() {
        return this.files;
    }

    /**
     * Adds new file into collection of files with this similarity.
     *
     * @param file File to be added.
     */
    public void add(File file) {
        this.files.add(file);
    }

    /**
     * Gets the label.
     *
     * @return The label.
     */
    @Override
    public String toString() {
        return this.label;
    }

    /**
     * Compares this object to other object.
     *
     * @param o Other object.
     * @return {@code true} if the argument is equal to this object and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        SimilarityElement that = (SimilarityElement) o;
        return Objects.equals(this.label, that.label) && Objects.equals(this.value, that.value);
    }

    /**
     * Computes a hash value based on label and value.
     *
     * @return A hash value of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.label, this.value);
    }
}
