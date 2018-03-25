package cz.zcu.kiv.accessvalidator.validator.database;

import java.io.File;
import java.util.*;

/**
 * @author ike
 */
public class SimilarFiles {
    private File file;
    private Map<File, List<SimilarityElement>> similarFiles = new HashMap<>();

    public SimilarFiles(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public Set<File> getSimilarFiles() {
        return this.similarFiles.keySet();
    }

    public List<SimilarityElement> getFileSimilarities(File file) {
        return this.similarFiles.getOrDefault(file, Collections.emptyList());
    }

    public void add(File similarFile, SimilarityElement similarityElement) {
        List<SimilarityElement> similarities = this.similarFiles.computeIfAbsent(similarFile, f -> new ArrayList<>());
        similarities.add(similarityElement);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SimilarFiles that = (SimilarFiles) o;
        return Objects.equals(this.file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.file);
    }
}
