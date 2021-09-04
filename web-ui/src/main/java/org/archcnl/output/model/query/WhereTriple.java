package org.archcnl.output.model.query;

import java.util.Objects;
import org.archcnl.output.model.query.attribute.QueryField;
import org.archcnl.output.model.query.attribute.QueryObject;
import org.archcnl.output.model.query.attribute.QueryPredicate;

/**
 * Representation of triple from SPARQL subject-predicate-object, for example: ?aggregate rdf:type
 * architecture:Aggregate
 */
public class WhereTriple implements FormattedQueryDomainObject, FormattedViewDomainObject {

    private QueryField subject;
    private QueryPredicate predicate;
    private QueryObject object;

    public WhereTriple(
            final QueryField subject, final QueryPredicate predicate, final QueryObject object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public QueryField getSubject() {
        return subject;
    }

    public QueryPredicate getPredicate() {
        return predicate;
    }

    public QueryObject getObject() {
        return object;
    }

    @Override
    public String asFormattedString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(subject.asFormattedString());
        sb.append(" ");
        sb.append(predicate.asFormattedString());
        sb.append(" ");
        sb.append(object.asFormattedString());
        return sb.toString();
    }

    @Override
    public String asFormattedQuery() {
        final StringBuffer sb = new StringBuffer();
        sb.append(subject.asFormattedQuery());
        sb.append(" ");
        sb.append(predicate.asFormattedQuery());
        sb.append(" ");
        sb.append(object.asFormattedQuery());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WhereTriple) {
            final WhereTriple that = (WhereTriple) obj;
            return Objects.equals(this.subject, that.subject)
                    && Objects.equals(this.predicate, that.predicate)
                    && Objects.equals(this.object, that.object);
        }
        return false;
    }
}
