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
public class AccdbRelationRepository {

    private Database db;
    private Set<Relationship> relations = Collections.emptySet();

    AccdbRelationRepository(Database db) {
        this.db = db;
        try {
            this.relations = new HashSet<>(db.getRelationships());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter11Relations() {
        this.relations.removeIf(relationship -> !relationship.isOneToOne());
    }

    public void filter1NRelations() {
        this.relations.removeIf(relationship -> relationship.isOneToOne());
    }

    public Set<Relationship> getRelations() {
        return this.relations;
    }



}
