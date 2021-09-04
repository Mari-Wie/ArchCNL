package org.archcnl.output.model.query.attribute;

import java.util.Objects;
import org.archcnl.output.model.query.FormattedQueryDomainObject;
import org.archcnl.output.model.query.FormattedViewDomainObject;
import org.archcnl.output.model.query.QueryNamesapace;

/** Representation of predicate from SPARQL triple. */
public class QueryPredicate implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private QueryNamesapace namespace;
    private String value;

    public QueryPredicate(final QueryNamesapace namespace, final String value) {
        this.namespace = namespace;
        this.value = value;
    }

    public QueryNamesapace getNamespace() {
        return namespace;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String asFormattedString() {
        return namespace.getAlias() + ":" + value;
    }

    @Override
    public String asFormattedQuery() {
        return asFormattedString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, value);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof QueryPredicate) {
            final QueryPredicate that = (QueryPredicate) obj;
            return Objects.equals(this.namespace, that.namespace)
                    && Objects.equals(this.value, that.value);
        }
        return false;
    }
}
