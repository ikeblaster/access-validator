package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.query.Query;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.QueryType;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public class AccdbQueryRepository {

    private Database db;
    private Set<Query> queries = Collections.emptySet();

    AccdbQueryRepository(Database db) {
        this.db = db;

        try {
            this.queries = new HashSet<>(this.db.getQueries());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterByType(QueryType queryType) {
        this.queries.removeIf(query -> !queryType.compare(query));
    }

    public Set<Query> getQueries() {
        return this.queries;
    }

}
