package org.archcnl.domain.output.model.query;

import java.util.Objects;
import java.util.Set;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;
import org.archcnl.domain.output.model.query.attribute.QueryNamespace;

/** Representation of query to database. */
public class Query implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private Set<QueryNamespace> namespaces;
    private SelectClause selectClause;
    private WhereClause whereClause;

    public Query(
            final Set<QueryNamespace> namespaces,
            final SelectClause selectClause,
            final WhereClause whereClause) {
        this.namespaces = namespaces;
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public SelectClause getSelectClause() {
        return selectClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    public Set<QueryNamespace> getNamespaces() {
        return namespaces;
    }

    @Override
    public String transformToGui() {
        final StringBuffer sb = new StringBuffer();
        namespaces.stream()
                .forEach(ns -> sb.append(ns.transformToSparqlQuery() + System.lineSeparator()));
        sb.append(selectClause.transformToGui());
        sb.append(System.lineSeparator());
        sb.append(whereClause.transformToGui());
        return sb.toString();
    }

    @Override
    public String transformToSparqlQuery() {
        final StringBuffer sb = new StringBuffer();
        namespaces.stream().forEach(ns -> sb.append(ns.transformToSparqlQuery() + " "));
        sb.append(selectClause.transformToSparqlQuery());
        sb.append(whereClause.transformToSparqlQuery());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaces, selectClause, whereClause);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Query) {
            final Query that = (Query) obj;
            return Objects.equals(this.namespaces, that.namespaces)
                    && Objects.equals(this.selectClause, that.selectClause)
                    && Objects.equals(this.whereClause, that.whereClause);
        }
        return false;
    }
}
