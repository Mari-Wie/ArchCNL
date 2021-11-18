package org.archcnl.domain.input.model.mappings;

public class Triplet {

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

    public String toStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        if (predicate instanceof JenaBuiltinRelation) {
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
