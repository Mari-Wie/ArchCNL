package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.TripletFactory;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.common.andtriplets.triplet.events.AddTripletViewAfterButtonPressedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.TripletViewDeleteButtonPressedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableFilterChangedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.ObjectNotDefinedException;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.SubjectOrObjectNotDefinedException;
import org.archcnl.ui.common.andtriplets.triplet.exceptions.TripletNotDefinedException;

@Tag("TripletPresenter")
public class TripletPresenter extends Component {

    private static final long serialVersionUID = 3517038691361279084L;
    private TripletView tripletView;

    public TripletPresenter() {
        tripletView = new TripletView();
        addListeners();
    }

    public void showTriplet(final Triplet triplet) {
        tripletView.getSubjectComponent().setVariable(triplet.getSubject());
        tripletView.getPredicateComponent().setPredicate(triplet.getPredicate());
        tripletView.getObjectView().setObject(triplet.getObject());
    }

    private void addListeners() {
        tripletView.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        tripletView.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        tripletView.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);

        tripletView.addListener(
                PredicateSelectedEvent.class,
                event ->
                        event.handleEvent(
                                RulesConceptsAndRelations.getInstance().getRelationManager(),
                                tripletView.getObjectView()));
        tripletView.addListener(
                RelationListUpdateRequestedEvent.class,
                event ->
                        event.handleEvent(
                                RulesConceptsAndRelations.getInstance()
                                        .getRelationManager()
                                        .getInputRelations()));

        tripletView.addListener(
                ConceptListUpdateRequestedEvent.class,
                event ->
                        event.handleEvent(
                                RulesConceptsAndRelations.getInstance()
                                        .getConceptManager()
                                        .getInputConcepts()));
        tripletView.addListener(
                ConceptSelectedEvent.class,
                event ->
                        event.handleEvent(
                                RulesConceptsAndRelations.getInstance().getConceptManager()));

        tripletView.addListener(TripletViewDeleteButtonPressedEvent.class, this::fireEvent);
        tripletView.addListener(AddTripletViewAfterButtonPressedEvent.class, this::fireEvent);
    }

    public Triplet getTriplet() throws TripletNotDefinedException, UnsupportedObjectTypeInTriplet {
        Variable subject;
        Optional<Relation> predicate;
        ObjectType object;
        try {
            subject = tripletView.getSubjectComponent().getVariable();
            final String relationName = tripletView.getPredicateComponent().getSelectedItem().get();
            predicate =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getRelationByName(relationName);
            object = tripletView.getObjectView().getObject();
        } catch (InvalidVariableNameException
                | SubjectOrObjectNotDefinedException
                | NoSuchElementException
                | ConceptDoesNotExistException
                | ObjectNotDefinedException e) {
            throw new TripletNotDefinedException();
        }
        if (predicate.isEmpty()) {
            throw new TripletNotDefinedException();
        } else {
            return TripletFactory.createTriplet(subject, predicate.get(), object);
        }
    }

    public boolean isIncomplete() {
        boolean subjectMissing = false;
        boolean predicateMissing = false;
        boolean objectMissing = false;
        try {
            tripletView.getSubjectComponent().getVariable();
        } catch (InvalidVariableNameException | SubjectOrObjectNotDefinedException e1) {
            subjectMissing = true;
        }
        try {
            final String relationName = tripletView.getPredicateComponent().getSelectedItem().get();
            final Optional<Relation> relationOpt =
                    RulesConceptsAndRelations.getInstance()
                            .getRelationManager()
                            .getRelationByName(relationName);
            predicateMissing = relationOpt.isEmpty();
        } catch (final NoSuchElementException e) {
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

    public TripletView getTripletView() {
        return tripletView;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
