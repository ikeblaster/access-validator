package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.query.Query;

import java.util.Arrays;
import java.util.List;

/**
 * Represents type of query saved in database.
 *
 * @author Vojtech Kinkor
 */
public enum QueryType {
    _ANY(null),
    SELECT(Query.Type.SELECT),
    MAKE_TABLE(Query.Type.MAKE_TABLE),
    APPEND(Query.Type.APPEND),
    UPDATE(Query.Type.UPDATE),
    DELETE(Query.Type.DELETE),
    CROSS_TAB(Query.Type.CROSS_TAB),
    DATA_DEFINITION(Query.Type.DATA_DEFINITION),
    PASSTHROUGH(Query.Type.PASSTHROUGH),
    UNION(Query.Type.UNION);

    /**
     * Database query type.
     */
    private Query.Type queryType;

    /**
     * Represents type of query saved in database.
     *
     * @param queryType Internal database query type.
     */
    QueryType(Query.Type queryType) {
        this.queryType = queryType;
    }

    /**
     * Gets label for query type.
     *
     * @return Label for query type.
     */
    @Override
    public String toString() {
        return this == _ANY ? "" : this.name();
    }

    /**
     * Gets all query types as collection.
     *
     * @return Collection of query types.
     */
    public static List<QueryType> getChoices() {
        return Arrays.asList(values());
    }

    /**
     * Compares with database query.
     *
     * @param query Database query.
     * @return {@code true} when equal, {@code} false otherwise.
     */
    public boolean compare(Query query) {
        if(this == _ANY) {
            return true;
        }
        return query.getType() == this.queryType;
    }

}