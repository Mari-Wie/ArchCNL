package org.archcnl.input.datatypes.mappings;

import org.archcnl.input.datatypes.mappings.Relation.RelationType;
import org.archcnl.input.exceptions.RecursiveRelationException;
import org.archcnl.input.exceptions.UnsupportedObjectTypeInTriplet;

public class Triplet {

    private Variable subject;
    private Relation predicate;
    private ObjectFromTriplet object;

    public Triplet(Variable subject, Relation predicate, String object)
            throws UnsupportedObjectTypeInTriplet {
        this.setSubject(subject);
        this.setPredicate(predicate);
        this.setObject(object);
    }

    public Triplet(Variable subject, Relation predicate, Concept object)
            throws UnsupportedObjectTypeInTriplet {
        this.setSubject(subject);
        this.setPredicate(predicate);
        this.setObject(object);
    }

    public Triplet(Variable subject, Relation predicate, Variable object)
            throws RecursiveRelationException, UnsupportedObjectTypeInTriplet {
        this.setSubject(subject);
        this.setPredicate(predicate);
        this.setObject(object);
    }

    public void setObject(String object) throws UnsupportedObjectTypeInTriplet {
        if (!doPredicateAndObjectMatch(object)) {
            throw new UnsupportedObjectTypeInTriplet();
        }
        this.object = new ObjectFromTriplet(object);
    }

    public void setObject(Concept object) throws UnsupportedObjectTypeInTriplet {
        if (!doPredicateAndObjectMatch(object)) {
            throw new UnsupportedObjectTypeInTriplet();
        }
        this.object = new ObjectFromTriplet(object);
    }

    public void setObject(Variable object)
            throws RecursiveRelationException, UnsupportedObjectTypeInTriplet {
        if (subject.sameNameAs(object)) {
            throw new RecursiveRelationException();
        }
        if (!doPredicateAndObjectMatch(object)) {
            throw new UnsupportedObjectTypeInTriplet();
        }
        this.object = new ObjectFromTriplet(object);
    }

    /**
     * Return value can be null. Exactly one of the three object getters will return something not
     * equal to zero.
     *
     * @return The string value of this object
     */
    public String getStringObject() {
        return object.getValue();
    }

    /**
     * Return value can be null. Exactly one of the three object getters will return something not
     * equal to zero.
     *
     * @return The variable of this object
     */
    public Variable getVariableObject() {
        return object.getVariable();
    }

    /**
     * Return value can be null. Exactly one of the three object getters will return something not
     * equal to zero.
     *
     * @return The concept of this object
     */
    public Concept getConceptObject() {
        return object.getConcept();
    }

    public Variable getSubject() {
        return subject;
    }

    public void setSubject(Variable subject) {
        this.subject = subject;
    }

    public Relation getPredicate() {
        return predicate;
    }

    public void setPredicate(Relation predicate) {
        this.predicate = predicate;
        this.object = null;
    }

    private boolean doPredicateAndObjectMatch(Concept object) {
        return predicate.canRelateToConcept(object);
    }

    private boolean doPredicateAndObjectMatch(Variable object) {
        // TODO implement variable type matching
        return true;
    }

    private boolean doPredicateAndObjectMatch(String object) {
        // TODO implement value type matching
        return true;
    }

    public String toStringRepresentation() {
        StringBuilder builder = new StringBuilder();
        if (predicate.getRelationType() == RelationType.matches) {
            builder.append("regex(");
            builder.append(subject.toStringRepresentation() + ", ");
            // A regex always needs a pattern as object
            // so there is no check for variableObject here
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
