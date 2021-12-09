package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.ui.input.mappingeditor.events.PredicateSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableSelectedEvent;
import org.archcnl.ui.input.mappingeditor.exceptions.TripletNotDefinedException;

@Tag("TripletPresenter")
public class TripletPresenter extends Component {

    private TripletView tripletView;
    private Triplet triplet;

    public TripletPresenter(Optional<Triplet> triplet, VariableManager variableManager) {
        // TODO fix optional
        if (triplet.isPresent()) {
            this.triplet = triplet.get();
            tripletView = new TripletView(this, variableManager, this.triplet);
        } else {
            tripletView = new TripletView(this, variableManager);
        }
        addListeners();
    }

    private void addListeners() {
        tripletView.addListener(
                VariableSelectedEvent.class,
                e -> {
                    System.out.println("received event in TripletPresenter, refireing");
                    fireEvent(e);
                });
        tripletView.addListener(
                VariableListUpdateRequestedEvent.class,
                e -> {
                    System.out.println("received event in TripletPresenter, refireing");
                    fireEvent(e);
                });
        tripletView.addListener(
                PredicateSelectedEvent.class,
                e -> {
                    System.out.println("received event in TripletPresenter, refireing");
                    fireEvent(e);
                });
    }

    public Triplet getTriplet() throws TripletNotDefinedException, UnsupportedObjectTypeInTriplet {
        Variable subject;
        Relation predicate;
        ObjectType object;
        /*
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
            */
        return null; // TripletFactory.createTriplet(subject, predicate, object);
    }

    public boolean isIncomplete() {
        boolean subjectMissing = false;
        boolean predicateMissing = false;
        boolean objectMissing = false;
        /*
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
        */
        return !(subjectMissing == predicateMissing && predicateMissing == objectMissing);
    }

    public void highlightIncompleteParts() {
        /*
        tripletView.getSubjectComponent().highlightWhenEmpty();
        tripletView.getPredicateComponent().highlightWhenEmpty();
        tripletView.getObjectView().highlightWhenEmpty();
        */
    }

    public TripletView getTripletView() {
        return tripletView;
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
