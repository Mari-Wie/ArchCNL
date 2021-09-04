package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.Objects;

/** Representation of query to database. */
public class Query implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private SelectClause selectClause;
    private WhereClause whereClause;

    public Query(final SelectClause selectClause, final WhereClause whereClause) {
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public SelectClause getSelectClause() {
        return selectClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    @Override
    public String asFormattedString() {
        final StringBuffer sb = new StringBuffer();
        addNamespacesAsFormattedString(sb);
        sb.append(selectClause.asFormattedString());
        sb.append(System.lineSeparator());
        sb.append(whereClause.asFormattedString());
        return sb.toString();
    }

    private void addNamespacesAsFormattedString(final StringBuffer sb) {
        Arrays.asList(QueryNamesapace.values()).stream()
                .forEach(n -> sb.append(n.asFormattedString() + System.lineSeparator()));
    }

    @Override
    public String asFormattedQuery() {
        final StringBuffer sb = new StringBuffer();
        addNamespacesAsFormattedQuery(sb);
        sb.append(selectClause.asFormattedQuery());
        sb.append(whereClause.asFormattedQuery());
        return sb.toString();
    }

    private void addNamespacesAsFormattedQuery(final StringBuffer sb) {
        Arrays.asList(QueryNamesapace.values()).stream()
                .forEach(n -> sb.append(n.asFormattedString() + " "));
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectClause, whereClause);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Query) {
            final Query that = (Query) obj;
            return Objects.equals(this.selectClause, that.selectClause)
                    && Objects.equals(this.whereClause, that.whereClause);
        }
        return false;
    }
}
