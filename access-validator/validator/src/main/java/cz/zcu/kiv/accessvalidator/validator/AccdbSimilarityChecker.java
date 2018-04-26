package cz.zcu.kiv.accessvalidator.validator;

import cz.zcu.kiv.accessvalidator.validator.database.Accdb;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarFiles;
import cz.zcu.kiv.accessvalidator.validator.database.SimilarityElement;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author ike
 */
public class AccdbSimilarityChecker {

    private List<Accdb> dbs = new ArrayList<>();
    private Map<Object, SimilarityElement> similarityGroups = new HashMap<>();
    private Set<SimilarityElement> hiddenSimilarities = Collections.emptySet();

    public AccdbSimilarityChecker(List<File> files) throws IOException {
        for(File file : files) {
            this.dbs.add(new Accdb(file));
        }

        this.process();
    }

    private void process() {
        for(int i = 0; i < this.dbs.size(); i++) {
            for(int j = i + 1; j < this.dbs.size(); j++) {
                this.compare(this.dbs.get(i), this.dbs.get(j));
            }
        }
    }

    private void compare(Accdb db1, Accdb db2) {
        Set<SimilarityElement> similarities = db1.findSimilarities(db2);

        for(SimilarityElement similarity : similarities) {
            SimilarityElement group = this.similarityGroups.computeIfAbsent(similarity, k -> similarity);

            group.add(db1.getFile());
            group.add(db2.getFile());
        }
    }


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

    public void setIgnoreSimilarities(Set<SimilarityElement> hiddenSimilarities) {
        this.hiddenSimilarities = hiddenSimilarities;
    }



}
