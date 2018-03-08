package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Relationship;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public class AccdbRelationsRepository {

    private Database db;
    private Set<Relationship> relationships = Collections.emptySet();

    AccdbRelationsRepository(Database db) {
        this.db = db;
        try {
            this.relationships = new HashSet<>(db.getRelationships());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Set<Relationship> getRelationships() {
        return this.relationships;
    }



}
