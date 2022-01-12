package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.input.mappingeditor.events.AddTripletViewAfterButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.ConceptSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.PredicateSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.TripletViewDeleteButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableCreationRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableFilterChangedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;

public class TripletView extends HorizontalLayout {

    private static final long serialVersionUID = -547117976123681486L;

    private VariableSelectionComponent subjectComponent;
    private PredicateComponent predicateComponent;
    private ObjectView objectView;

    public TripletView() {
        setPadding(false);
        setWidthFull();

        subjectComponent = new VariableSelectionComponent();
        predicateComponent = new PredicateComponent();
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

    public PredicateComponent getPredicateComponent() {
        return predicateComponent;
    }

    public ObjectView getObjectView() {
        return objectView;
    }

    private void addListeners() {
        subjectComponent.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        subjectComponent.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);

        predicateComponent.addListener(PredicateSelectedEvent.class, this::fireEvent);
        predicateComponent.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);

        objectView.addListener(VariableFilterChangedEvent.class, this::fireEvent);
        objectView.addListener(VariableCreationRequestedEvent.class, this::fireEvent);
        objectView.addListener(VariableListUpdateRequestedEvent.class, this::fireEvent);
        objectView.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        objectView.addListener(ConceptSelectedEvent.class, this::fireEvent);
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
