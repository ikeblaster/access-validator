package cz.zcu.kiv.accessvalidator.validator.database;

import java.io.File;
import java.util.*;

/**
 * Represents a file together with all files similar to it.
 *
 * @author ike
 */
public class SimilarFiles {

    private File file;
    private Map<File, List<SimilarityElement>> similarFiles = new HashMap<>();

    /**
     * Represents a file together with all files similar to it.
     *
     * @param file Main file.
     */
    public SimilarFiles(File file) {
        this.file = file;
    }

    /**
     * Gets the main file.
     *
     * @return Main file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Gets all files similar to the main file.
     *
     * @return Set of similar files.
     */
    public Set<File> getSimilarFiles() {
        return this.similarFiles.keySet();
    }

    /**
     * Gets collection of similarities between the main file and other file.
     *
     * @param file Other file.
     * @return Collection of similarities.
     */
    public List<SimilarityElement> getFileSimilarities(File file) {
        return this.similarFiles.getOrDefault(file, Collections.emptyList());
    }

    /**
     * Adds similar file to the main file along with similarity element they share.
     *
     * @param similarFile Similar file.
     * @param similarityElement Similarity it shares with main file.
     */
    public void add(File similarFile, SimilarityElement similarityElement) {
        List<SimilarityElement> similarities = this.similarFiles.computeIfAbsent(similarFile, f -> new ArrayList<>());
        similarities.add(similarityElement);
    }

    /**
     * Compares this object to other object.
     *
     * @param o Other object.
     * @return {@code true} if the argument is equal to this object and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SimilarFiles that = (SimilarFiles) o;
        return Objects.equals(this.file, that.file);
    }

    /**
     * Computes a hash value based only on the main file.
     *
     * @return A hash value of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.file);
    }
}
