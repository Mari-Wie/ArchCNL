package org.archcnl.domain.output.model.query;

import java.util.Objects;
import org.archcnl.domain.common.AndTriplets;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.FormattedViewDomainObject;

/** Representation of SPARQL WHERE clause */
public class WhereClause implements FormattedQueryDomainObject, FormattedViewDomainObject {

    public static final String WHERE = "WHERE";
    public static final String GRAPH = "GRAPH";
    public static final String GRAPH_FIELD = "?g";
    public static final String OPEN_BRACKET = "{";
    public static final String CLOSE_BRACKET = "}";
    public static final String TAB = "  ";
    public static final String NEW_LINE = System.lineSeparator();

    private AndTriplets andTriplets;

    public WhereClause(final AndTriplets andTriplets) {
        this.andTriplets = andTriplets;
    }

    public AndTriplets getAndTriplets() {
        return andTriplets;
    }

    @Override
    public int hashCode() {
        return Objects.hash(andTriplets);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WhereClause) {
            final WhereClause that = (WhereClause) obj;
            return Objects.equals(this.andTriplets, that.andTriplets);
        }
        return false;
    }

    @Override
    public String transformToGui() {
        final StringBuilder sb = new StringBuilder();
        sb.append(WhereClause.WHERE);
        sb.append(" ");
        sb.append(WhereClause.OPEN_BRACKET);
        sb.append(WhereClause.NEW_LINE);
        sb.append(WhereClause.TAB);
        sb.append(WhereClause.GRAPH);
        sb.append(" ");
        sb.append(WhereClause.GRAPH_FIELD);
        sb.append(" ");
        sb.append(WhereClause.OPEN_BRACKET);
        sb.append(WhereClause.NEW_LINE);
        sb.append(andTriplets.transformToGui(WhereClause.TAB, WhereClause.NEW_LINE));
        sb.append(WhereClause.TAB);
        sb.append(WhereClause.CLOSE_BRACKET);
        sb.append(WhereClause.NEW_LINE);
        sb.append(WhereClause.CLOSE_BRACKET);
        return sb.toString();
    }

    @Override
    public String transformToSparqlQuery() {
        final StringBuilder sb = new StringBuilder();
        sb.append(WhereClause.WHERE);
        sb.append(" ");
        sb.append(WhereClause.OPEN_BRACKET);
        sb.append(" ");
        sb.append(WhereClause.GRAPH);
        sb.append(" ");
        sb.append(WhereClause.GRAPH_FIELD);
        sb.append(" ");
        sb.append(WhereClause.OPEN_BRACKET);
        sb.append(andTriplets.transformToSparqlQuery());
        sb.append(" ");
        sb.append(WhereClause.CLOSE_BRACKET);
        sb.append(WhereClause.CLOSE_BRACKET);
        return sb.toString();
    }
}
