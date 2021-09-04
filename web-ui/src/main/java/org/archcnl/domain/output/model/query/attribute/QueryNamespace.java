package org.archcnl.domain.output.model.query.attribute;

import java.util.Objects;
import org.archcnl.domain.output.model.query.FormattedQueryDomainObject;
import org.archcnl.domain.output.model.query.FormattedViewDomainObject;

/** Representation of standard for ArchCNL namespaces in SPARQL query. */
public class QueryNamespace implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private String alias;
    private String uri;

    public QueryNamespace(final String alias, final String uri) {
        this.alias = alias;
        this.uri = uri;
    }

    public String getAlias() {
        return alias;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String asFormattedString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PREFIX ");
        sb.append(alias);
        sb.append(" <");
        sb.append(uri);
        sb.append("#>");
        return sb.toString();
    }

    @Override
    public String asFormattedQuery() {
        return asFormattedString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, uri);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof QueryNamespace) {
            final QueryNamespace that = (QueryNamespace) obj;
            return Objects.equals(this.alias, that.alias) && Objects.equals(this.uri, that.uri);
        }
        return false;
    }
}
