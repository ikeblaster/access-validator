package cz.zcu.kiv.accessvalidator.validator.rules.properties;

import com.healthmarketscience.jackcess.query.Query;

import java.util.Arrays;
import java.util.List;

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

    private Query.Type queryType;

    QueryType(Query.Type queryType) {
        this.queryType = queryType;
    }

    @Override
    public String toString() {
        return this == _ANY ? "" : this.name();
    }

    public static List<QueryType> getChoices() {
        return Arrays.asList(values());
    }

    public boolean compare(Query query) {
        if(this == _ANY) {
            return true;
        }
        return query.getType() == this.queryType;
    }

}