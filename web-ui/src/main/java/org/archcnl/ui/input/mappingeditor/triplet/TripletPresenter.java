package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableDoesNotExistException;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Relation;
import org.archcnl.domain.input.model.mappings.Triplet;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

public class TripletPresenter implements TripletContract.Presenter<View> {

    private static final long serialVersionUID = -7565399174691810818L;
    private SubjectPresenter subjectPresenter;
    private PredicatePresenter predicatePresenter;
    private ObjectPresenter objectPresenter;
    private View view;

    @Override
    public void setSubjectPresenter(SubjectPresenter subjectPresenter) {
        this.subjectPresenter = subjectPresenter;
    }

    @Override
    public void setPredicatePresenter(PredicatePresenter predicatePresenter) {
        this.predicatePresenter = predicatePresenter;
    }

    @Override
    public void setObjectPresenter(ObjectPresenter objectPresenter) {
        this.objectPresenter = objectPresenter;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    public Triplet getTriplet() throws TripletNotDefinedException, UnsupportedObjectTypeInTriplet {
        Variable subject;
        Relation predicate;
        ObjectType object;
        try {
            subject = subjectPresenter.getSubject();
            predicate = predicatePresenter.getPredicate();
            object = objectPresenter.getObject();
        } catch (VariableDoesNotExistException
                | SubjectNotDefinedException
                | RelationDoesNotExistException
                | RelationNotDefinedException
                | ConceptDoesNotExistException
                | ObjectNotDefinedException e) {
            throw new TripletNotDefinedException();
        }
        return new Triplet(subject, predicate, object);
    }
}
