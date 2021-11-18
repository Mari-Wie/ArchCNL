package org.archcnl.domain.output.model.query.attribute;

import java.util.Objects;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;

/** Representation of predicate from SPARQL triple. */
public class QueryPredicate implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private String namespaceAlias;
    private String value;

    public QueryPredicate(final String namespaceAlias, final String value) {
        this.namespaceAlias = namespaceAlias;
        this.value = value;
    }

    public String getNamespaceAlias() {
        return namespaceAlias;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String transformToGui() {
        return namespaceAlias + ":" + value;
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToGui();
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceAlias, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof QueryPredicate) {
            final QueryPredicate that = (QueryPredicate) obj;
            return Objects.equals(this.namespaceAlias, that.namespaceAlias)
                    && Objects.equals(this.value, that.value);
        }
        return false;
    }
}
