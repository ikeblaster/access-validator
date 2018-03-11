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
    private Map<SimilarityElement, SimilarFiles> similarityGroups = new HashMap<>();

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
            SimilarFiles simFiles = this.similarityGroups.computeIfAbsent(similarity, k -> new SimilarFiles(similarity));

            simFiles.add(db1.getFile());
            simFiles.add(db2.getFile());
        }

    }

    public Map<File, List<SimilarFiles>> getSimilarFiles() {
        Map<File, List<SimilarFiles>> map = new HashMap<>();

        // create map [file => [all files similar to it]]
        for (SimilarFiles similarity : this.similarityGroups.values()) {

            for(File file : similarity.getFiles()) {
                List<SimilarFiles> allSimilarities = map.computeIfAbsent(file, f -> new ArrayList<>());
                allSimilarities.add(similarity);
            }

        }

        return map;
    }

}
