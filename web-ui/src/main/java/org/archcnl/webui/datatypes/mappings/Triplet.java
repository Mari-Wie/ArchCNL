package org.archcnl.webui.datatypes.mappings;

import org.archcnl.webui.exceptions.ObjectIsNotAConceptException;
import org.archcnl.webui.exceptions.ObjectIsNotAVariableException;
import org.archcnl.webui.exceptions.RecursiveRelationException;
import org.archcnl.webui.exceptions.RelationCannotRelateToObjectException;

public class Triplet {

    private Variable subject;
    private Relation predicate;
    private Concept conceptObject;
    private Variable variableObject;

    public Triplet(Variable subject, Relation predicate, Concept object)
            throws RelationCannotRelateToObjectException {
        if (!doPredicateAndObjectMatch(predicate, object)) {
            throw new RelationCannotRelateToObjectException(predicate, object);
        }
        this.setSubject(subject);
        this.setPredicate(predicate);
        this.setObject(object);
    }

    public Triplet(Variable subject, Relation predicate, Variable object)
            throws RecursiveRelationException {
        this.setSubject(subject);
        this.setPredicate(predicate);
        this.setObject(object);
    }

    public void setObject(Concept object) {
        variableObject = null;
        conceptObject = object;
    }

    public void setObject(Variable object) throws RecursiveRelationException {
        if (subject.sameNameAs(object)) {
            throw new RecursiveRelationException();
        }
        variableObject = object;
        conceptObject = null;
    }

    public Concept getConceptObject() throws ObjectIsNotAConceptException {
        if (conceptObject == null) {
            throw new ObjectIsNotAConceptException();
        }
        return conceptObject;
    }

    public Variable getVariableObject() throws ObjectIsNotAVariableException {
        if (variableObject == null) {
            throw new ObjectIsNotAVariableException();
        }
        return variableObject;
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
    }

    private boolean doPredicateAndObjectMatch(Relation predicate, Concept object) {
        return predicate.canRelateToConcept(object);
    }
}
