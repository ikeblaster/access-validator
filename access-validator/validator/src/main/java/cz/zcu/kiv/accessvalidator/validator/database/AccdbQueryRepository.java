package cz.zcu.kiv.accessvalidator.validator.database;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.query.Query;
import cz.zcu.kiv.accessvalidator.validator.rules.properties.QueryType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ike
 */
public class AccdbQueryRepository {

    private static final int FORM_QUERY_OBJECT_FLAG = 3;

    private Database db;
    private Set<Query> queries;

    AccdbQueryRepository(Database db) {
        this.db = db;
        try {
            this.queries = new HashSet<>(this.db.getQueries());
            this.queries.removeIf(query -> query.getName().startsWith("~")); // exclude queries from forms
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filterByType(QueryType queryType) {
        this.queries.removeIf(query -> !queryType.compare(query));
    }

    public Set<Query> getQueries() {
        return this.queries;
    }

}
