package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Provides a tool for batch checking multiple files for similarities.
 *
 * @author Vojtech Kinkor
 */
public class AccdbSimilarityChecker {

    /**
     * List of databases being checked.
     */
    private List<Accdb> dbs = new ArrayList<>();

    /**
     * Collection of similarities. Map is used for searching already found similarity element, hence key is also {@code SimilarityElement}.
     */
    private Map<Object, SimilarityElement> similarityGroups = new HashMap<>();

    /**
     * Set of similarity elements which should be excluded from final collection.
     */
    private Set<SimilarityElement> hiddenSimilarities = Collections.emptySet();

    /**
     * Provides a tool for batch checking multiple files for similarities.
     *
     * @param files List of files to be checked.
     * @throws IOException
     */
    public AccdbSimilarityChecker(List<File> files) throws IOException {
        for(File file : files) {
            this.dbs.add(new Accdb(file));
        }

        this.process();
    }

    /**
     * Gets a map of all checked files and collections of their similarities with other files.
     * Map structure: [file => [all similarities with other files { list of similar files }]]
     *
     * @return Map of files and their similarities to other files.
     */
    public Map<File, Collection<SimilarityElement>> getFileSimilarities() {
        Map<File, Collection<SimilarityElement>> map = new HashMap<>();

        // create map [file => [all similarities with other files { list of similar files }]]
        for (SimilarityElement group : this.similarityGroups.values()) {

            if(this.hiddenSimilarities.contains(group)) {
                continue;
            }

            for(File file : group.getFiles()) {
                Collection<SimilarityElement> allSimilarities = map.computeIfAbsent(file, f -> new ArrayList<>()); // add file to map is absent
                allSimilarities.add(group);
            }

        }

        for (Collection<SimilarityElement> elements : map.values()) {
            ((List<SimilarityElement>) elements).sort(Comparator.comparing(SimilarityElement::toString));
        }

        return map;
    }

    /**
     * Gets a map of files and structure {@code SimilarFiles} which holds collection
     * of similar files to the key file and similarities between them.
     *
     * @return Map of files and {@code SimilarFiles}.
     */
    public Map<File, SimilarFiles> getSimilarFiles() {
        Map<File, SimilarFiles> map = new HashMap<>();

        // create map [file => [all files similar to it { list of similarities }]]
        for (SimilarityElement similarity : this.similarityGroups.values()) {
            if(this.hiddenSimilarities.contains(similarity)) {
                continue;
            }

            for(File file : similarity.getFiles()) {

                SimilarFiles similarFiles = map.computeIfAbsent(file, f -> new SimilarFiles(file)); // add file to map is absent

                for(File similarFile : similarity.getFiles()) {
                    if(file == similarFile) continue;

                    similarFiles.add(similarFile, similarity);
                }

            }
        }

        return map;
    }

    /**
     * Sets similarity elements which should be excluded from final collection.
     *
     * @param hiddenSimilarities Collection of similarities to be excluded.
     */
    public void setIgnoreSimilarities(Set<SimilarityElement> hiddenSimilarities) {
        this.hiddenSimilarities = hiddenSimilarities;
    }

    /**
     * Compares all databases between each other.
     */
    private void process() {
        for(int i = 0; i < this.dbs.size(); i++) {
            for(int j = i + 1; j < this.dbs.size(); j++) {
                this.compare(this.dbs.get(i), this.dbs.get(j));
            }
        }
    }

    /**
     * Compares two databases.
     *
     * @param db1 First database.
     * @param db2 Second database.
     */
    private void compare(Accdb db1, Accdb db2) {
        Set<SimilarityElement> similarities = db1.findSimilarities(db2);

        for(SimilarityElement similarity : similarities) {
            SimilarityElement group = this.similarityGroups.computeIfAbsent(similarity, k -> similarity);

            group.add(db1.getFile());
            group.add(db2.getFile());
        }
    }





}
