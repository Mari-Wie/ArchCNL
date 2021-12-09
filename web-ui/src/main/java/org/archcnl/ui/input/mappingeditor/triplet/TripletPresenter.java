package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.Optional;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorContract;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.PredicateCannotRelateToObjectException;
import org.archcnl.ui.input.mappingeditor.exceptions.RelationNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;

public class TripletPresenter {

    private TripletView tripletView;
    private AndTripletsEditorContract.Presenter<AndTripletsEditorContract.View>
            andTripletsEditorPresenter;
    private Optional<Triplet> triplet;

    public TripletPresenter(
            AndTripletsEditorContract.Presenter<AndTripletsEditorContract.View>
                    andTripletsEditorPresenter,
            Optional<Triplet> triplet,
            VariableManager variableManager) {
        this.andTripletsEditorPresenter = andTripletsEditorPresenter;
        this.triplet = triplet;

        tripletView = new TripletView(variableManager);
        fillTriplet();
    }

    private void fillTriplet() {
        if (triplet.isPresent()) {
            tripletView.getSubjectComponent().setSubject(triplet.get().getSubject());
            tripletView.getPredicateComponent().setPredicate(triplet.get().getPredicate());
            try {
                tripletView.getObjectView().setObject(triplet.get().getObject());
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
            subject = tripletView.getSubjectComponent().getSubject();
            predicate = tripletView.getPredicateComponent().getPredicate();
            object = tripletView.getObjectView().getObject();
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

    public boolean isIncomplete() {
        boolean subjectMissing = false;
        boolean predicateMissing = false;
        boolean objectMissing = false;
        try {
            tripletView.getSubjectComponent().getSubject();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e1) {
            subjectMissing = true;
        }
        try {
            tripletView.getPredicateComponent().getPredicate();
        } catch (RelationDoesNotExistException | RelationNotDefinedException e) {
            predicateMissing = true;
        }
        try {
            tripletView.getObjectView().getObject();
        } catch (ConceptDoesNotExistException
                | ObjectNotDefinedException
                | InvalidVariableNameException
                | SubjectOrObjectNotDefinedException e) {
            objectMissing = true;
        }
        return !(subjectMissing == predicateMissing && predicateMissing == objectMissing);
    }

    public void highlightIncompleteParts() {
        tripletView.getSubjectComponent().highlightWhenEmpty();
        tripletView.getPredicateComponent().highlightWhenEmpty();
        tripletView.getObjectView().highlightWhenEmpty();
    }

    public void deleteButtonPressed() {
        andTripletsEditorPresenter.deleteTripletView(view);
    }

    public void addButtonPressed() {
        andTripletsEditorPresenter.addNewTripletViewAfter(view);
    }
}
