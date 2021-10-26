package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.Optional;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.domain.input.model.mappings.Relation;
import org.archcnl.domain.input.model.mappings.Triplet;
import org.archcnl.domain.input.model.mappings.TripletFactory;
import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

public class TripletPresenter implements TripletContract.Presenter<View> {

    private static final long serialVersionUID = -7565399174691810818L;
    private SubjectPresenter subjectPresenter;
    private PredicatePresenter predicatePresenter;
    private ObjectPresenter objectPresenter;
    private View view;
    private AndTripletsEditorContract.Presenter<AndTripletsEditorContract.View>
            andTripletsEditorPresenter;
    private Optional<Triplet> triplet;

    public TripletPresenter(
            AndTripletsEditorContract.Presenter<AndTripletsEditorContract.View>
                    andTripletsEditorPresenter,
            Optional<Triplet> triplet) {
        this.andTripletsEditorPresenter = andTripletsEditorPresenter;
        this.triplet = triplet;
    }

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
        fillTriplet();
    }

    private void fillTriplet() {
        if (triplet.isPresent()) {
            subjectPresenter.setSubject(triplet.get().getSubject());
            predicatePresenter.setPredicate(triplet.get().getPredicate());
            try {
                objectPresenter.setObject(triplet.get().getObject());
            } catch (PredicateCannotRelateToObjectException e) {
                // do not set the object
            }
        }
    }

    public Triplet getTriplet() throws TripletNotDefinedException, UnsupportedObjectTypeInTriplet {
        Variable subject;
        Relation predicate;
        ObjectType object;
        try {
            subject = subjectPresenter.getSubject();
            predicate = predicatePresenter.getPredicate();
            object = objectPresenter.getObject();
        } catch (InvalidVariableNameException
                | SubjectOrObjectNotDefinedException
                | RelationDoesNotExistException
                | RelationNotDefinedException
                | ConceptDoesNotExistException
                | ObjectNotDefinedException e) {
            throw new TripletNotDefinedException();
        }
        return TripletFactory.createTriplet(subject, predicate, object);
    }

    @Override
    public void mouseEnter() {
        view.setAddButtonVisible(true);
    }

    @Override
    public void mouseLeave() {
        view.setAddButtonVisible(false);
    }

    @Override
    public void deleteButtonPressed() {
        andTripletsEditorPresenter.deleteTripletView(view);
    }

    @Override
    public void addButtonPressed() {
        andTripletsEditorPresenter.addNewTripletViewAfter(view);
    }
}
