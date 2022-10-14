package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import java.util.Objects;
import org.archcnl.domain.common.FormattedDomainObject;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;

public class Triplet implements FormattedDomainObject {

    private Variable subject;
    private Relation predicate;
    private ObjectType object;

    public Triplet(Variable subject, Relation predicate, ObjectType object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Variable getSubject() {
        return subject;
    }

    public Relation getPredicate() {
        return predicate;
    }

    public ObjectType getObject() {
        return object;
    }

    @Override
    public String transformToAdoc() {
        StringBuilder builder = new StringBuilder();
        if (predicate instanceof JenaBuiltinRelation) {
            builder.append(predicate.transformToAdoc());
            builder.append("(");
            builder.append(subject.transformToAdoc() + ", ");
            builder.append(object.transformToAdoc());
            builder.append(")");
        } else {
            builder.append("(");
            builder.append(subject.transformToAdoc() + " ");
            builder.append(predicate.transformToAdoc() + " ");
            builder.append(object.transformToAdoc());
            builder.append(")");
        }
        return builder.toString();
    }

    @Override
    public String transformToGui() {
        final StringBuilder sb = new StringBuilder();
        sb.append(subject.transformToGui());
        sb.append(" ");
        sb.append(predicate.transformToGui());
        sb.append(" ");
        sb.append(object.transformToGui());
        sb.append(".");
        return sb.toString();
    }

    @Override
    public String transformToSparqlQuery() {
        final StringBuilder sb = new StringBuilder();
        FormattedQueryDomainObject parsedPredicate = (FormattedQueryDomainObject) predicate;
        sb.append(subject.transformToSparqlQuery());
        sb.append(" ");
        sb.append(parsedPredicate.transformToSparqlQuery());
        sb.append(" ");
        sb.append(object.transformToSparqlQuery());
        sb.append(".");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Triplet) {
            final Triplet that = (Triplet) obj;
            return Objects.equals(this.subject, that.subject)
                    && Objects.equals(this.predicate, that.predicate)
                    && Objects.equals(this.object, that.object);
        }
        return false;
    }

    @Override
    public String toString() {
        return transformToAdoc();
    }
}
