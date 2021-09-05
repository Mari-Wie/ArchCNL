package org.archcnl.domain.input.datatypes.mappings;

import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;

public class Triplet {

    private Variable subject;
    private Relation predicate;
    private ObjectType object;

    public Triplet(Variable subject, Relation predicate, ObjectType object)
            throws UnsupportedObjectTypeInTriplet {
        setSubject(subject);
        setPredicate(predicate);
        setObject(object);
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

    public void setSubject(Variable subject) {
        this.subject = subject;
    }

    public void setPredicate(Relation predicate) {
        this.predicate = predicate;
        if (!predicate.canRelateToObjectType(object)) {
            object = null;
        }
    }

    public void setObject(ObjectType object) throws UnsupportedObjectTypeInTriplet {
        if (predicate.canRelateToObjectType(object)) {
            this.object = object;
        } else {
            throw new UnsupportedObjectTypeInTriplet(predicate, object);
        }
    }

    public String toStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        if (predicate instanceof SpecialRelation) {
            builder.append(predicate.toStringRepresentation());
            builder.append("(");
            builder.append(subject.toStringRepresentation() + ", ");
            builder.append(object.toStringRepresentation());
            builder.append(")");
        } else {
            builder.append("(");
            builder.append(subject.toStringRepresentation() + " ");
            builder.append(predicate.toStringRepresentation() + " ");
            builder.append(object.toStringRepresentation());
            builder.append(")");
        }
        return builder.toString();
    }
}
