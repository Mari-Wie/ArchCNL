package org.archcnl.domain.common.io.exporthelper;

import java.util.List;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;

public class QueryToStringTransformer {

    private QueryToStringTransformer() {}

    public static String constructCustomQueryString(List<Query> queries) {
        StringBuilder builder = new StringBuilder();
        for (Query query : queries) {
            builder.append("[role=\"customQuery\"]");
            builder.append("\n");
            builder.append(query.transformToAdoc());
            builder.append("\n\n");
        }
        return builder.toString();
    }

    public static String constructFullTextQueryString(List<FreeTextQuery> queries) {
        StringBuilder builder = new StringBuilder();
        for (FreeTextQuery query : queries) {
            builder.append("[role=\"freeTextQuery\"]");
            builder.append("\n");
            builder.append(query.transformToAdoc());
            builder.append("\n\n");
        }
        return builder.toString();
    }
}
