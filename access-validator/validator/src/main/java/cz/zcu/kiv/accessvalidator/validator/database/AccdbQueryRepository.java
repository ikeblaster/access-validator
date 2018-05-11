package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.query.Query;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.QueryType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a repository for searching queries in ACCDB database.
 *
 * @author Vojtech Kinkor
 */
public class AccdbQueryRepository {

    /**
     * Database instance.
     */
    private Database db;

    /**
     * Current set of queries.
     */
    private Set<Query> queries;

    /**
     * Represents a repository for searching queries in ACCDB database.
     *
     * @param db Database instance.
     */
    AccdbQueryRepository(Database db) {
        this.db = db;
        try {
            this.queries = new HashSet<>(this.db.getQueries());
            this.queries.removeIf(query -> query.getName().startsWith(Accdb.INTERNAL_OBJECTS_PREFIX)); // exclude internal queries (e.g. form queries)
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Filters current set of queries by type.
     *
     * @param queryType Type of queries.
     */
    public void filterByType(QueryType queryType) {
        this.queries.removeIf(query -> !queryType.compare(query));
    }

    /**
     * Gets filtered set of queries.
     *
     * @return Set of found queries.
     */
    public Set<Query> getQueries() {
        return this.queries;
    }

}
