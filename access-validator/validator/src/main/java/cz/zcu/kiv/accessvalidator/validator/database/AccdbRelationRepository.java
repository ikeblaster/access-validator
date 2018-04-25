package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Relationship;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a repository for searching relationships in ACCDB database.
 *
 * @author ike
 */
public class AccdbRelationRepository {

    private static final String INTERNAL_PREFIX = "~";

    /**
     * Database instance.
     */
    private Database db;

    /**
     * Current set of relantionships.
     */
    private Set<Relationship> relations;

    /**
     * Represents a repository for searching relationships in ACCDB database.
     *
     * @param db Database instance.
     */
    AccdbRelationRepository(Database db) {
        this.db = db;
        try {
            this.relations = new HashSet<>(db.getRelationships());
            this.relations.removeIf(relation -> relation.getName().startsWith(INTERNAL_PREFIX)); // exclude internal relations
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters out anything else other than 1:1 relations.
     */
    public void filter11Relations() {
        this.relations.removeIf(relationship -> !relationship.isOneToOne());
    }

    /**
     * Filters out anything else other than 1:N relations.
     */
    public void filter1NRelations() {
        this.relations.removeIf(Relationship::isOneToOne);
    }

    /**
     * Gets filtered set of relationships.
     *
     * @return Set of found relationships.
     */
    public Set<Relationship> getRelations() {
        return this.relations;
    }



}
