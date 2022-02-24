package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.TripletFactory;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.InvalidVariableNameException;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.UnsupportedObjectTypeException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
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
                event -> {
                    event.setObjectView(tripletView.getObjectView());
                    fireEvent(event);
                });
        tripletView.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);

        tripletView.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        tripletView.addListener(ConceptSelectedEvent.class, this::fireEvent);

        tripletView.addListener(TripletViewDeleteButtonPressedEvent.class, this::fireEvent);
        tripletView.addListener(AddTripletViewAfterButtonPressedEvent.class, this::fireEvent);
    }

    public Triplet getTriplet() throws TripletNotDefinedException, UnsupportedObjectTypeException {
        Variable subject;
        Optional<Relation> predicate;
        ObjectType object;
        try {
            subject = tripletView.getSubjectComponent().getVariable();
            predicate = tripletView.getPredicateComponent().getRelation();
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
        predicateMissing = tripletView.getPredicateComponent().getRelation().isEmpty();
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
