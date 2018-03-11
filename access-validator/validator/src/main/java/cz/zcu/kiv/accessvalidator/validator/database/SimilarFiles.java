package cz.zcu.kiv.accessvalidator.validator.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ike
 */
public class SimilarFiles {
    private final SimilarityElement similarityElement;
    private List<File> files = new ArrayList<>();

    public SimilarFiles(SimilarityElement similarityElement) {
        this.similarityElement = similarityElement;
    }

    public SimilarityElement getSimilarityElement() {
        return this.similarityElement;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public void add(File file) {
        this.files.add(file);
    }

    @Override
    public String toString() {
        return this.similarityElement.toString();
    }

}
