package org.archcnl.ui.common.andtriplets.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.common.andtriplets.triplet.events.AddTripletViewAfterButtonPressedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.TripletViewDeleteButtonPressedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableCreationRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.VariableSelectedEvent;

public class TripletView extends HorizontalLayout {

    private static final long serialVersionUID = -547117976123681486L;

    private VariableSelectionComponent subjectComponent;
    private PredicateSelectionComponent predicateComponent;
    private ObjectView objectView;

    public TripletView() {
        setPadding(false);
        setWidthFull();

        subjectComponent = new VariableSelectionComponent();
        predicateComponent = new PredicateSelectionComponent();
        objectView = new ObjectView();

        HorizontalLayout tripletLayout = new HorizontalLayout();
        tripletLayout.setWidthFull();
        tripletLayout.add(subjectComponent);
        tripletLayout.add(predicateComponent);
        tripletLayout.add(objectView);
        add(tripletLayout);

        addCreateRemoveButtons();

        addListeners();
    }

    public VariableSelectionComponent getSubjectComponent() {
        return subjectComponent;
    }

    public PredicateSelectionComponent getPredicateComponent() {
        return predicateComponent;
    }

    public ObjectView getObjectView() {
        return objectView;
    }

    private void addListeners() {
        subjectComponent.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableSelectedEvent.class, this::fireEvent);

        predicateComponent.addListener(PredicateSelectedEvent.class, this::fireEvent);
        predicateComponent.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);

        objectView.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        objectView.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
        objectView.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        objectView.addListener(ConceptSelectedEvent.class, this::fireEvent);
        objectView.addListener(VariableSelectedEvent.class, this::fireEvent);
    }

    private void addCreateRemoveButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new TripletViewDeleteButtonPressedEvent(this))));
        buttons.add(
                new Button(
                        new Icon(VaadinIcon.PLUS),
                        click -> fireEvent(new AddTripletViewAfterButtonPressedEvent(this))));
        add(buttons);
        setVerticalComponentAlignment(Alignment.END, buttons);
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public void setLabels() {
        objectView.setLabel();
        subjectComponent.setLabel("Subject");
        predicateComponent.setLabel("Predicate");
    }
}
