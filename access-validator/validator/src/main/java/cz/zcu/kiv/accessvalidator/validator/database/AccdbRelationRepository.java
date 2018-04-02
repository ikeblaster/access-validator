package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Relationship;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public class AccdbRelationRepository {

    private Database db;
    private Set<Relationship> relations;

    AccdbRelationRepository(Database db) {
        this.db = db;
        try {
            this.relations = new HashSet<>(db.getRelationships());
            this.relations.removeIf(relation -> relation.getName().startsWith("~"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filter11Relations() {
        this.relations.removeIf(relationship -> !relationship.isOneToOne());
    }

    public void filter1NRelations() {
        this.relations.removeIf(Relationship::isOneToOne);
    }

    public Set<Relationship> getRelations() {
        return this.relations;
    }



}
