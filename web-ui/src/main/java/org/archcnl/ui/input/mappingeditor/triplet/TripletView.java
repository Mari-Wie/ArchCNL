package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.Triplet;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.ui.input.mappingeditor.events.AddTripletViewAfterButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.PredicateSelectedEvent;
import org.archcnl.ui.input.mappingeditor.events.TripletViewDeleteButtonPressedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableListUpdateRequestedEvent;
import org.archcnl.ui.input.mappingeditor.events.VariableSelectedEvent;

public class TripletView extends HorizontalLayout {

    private static final long serialVersionUID = -547117976123681486L;

    HorizontalLayout tripletLayout = new HorizontalLayout();
    private VariableSelectionComponent subjectComponent;
    private PredicateComponent predicateComponent;
    private ObjectView objectView;

    private void initLayoutFormat() {
        setPadding(false);
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        setWidthFull();
    }

    private void addListeners() {
        subjectComponent.addListener(
                VariableSelectedEvent.class,
                e -> {
                    System.out.println("refired VariableSelectedEvent in TripletView");
                    fireEvent(e);
                });
        subjectComponent.addListener(
                VariableListUpdateRequestedEvent.class,
                e -> {
                    System.out.println("refired VariableListUpdateRequestedEvent in TripletView");
                    fireEvent(e);
                });
        subjectComponent.addListener(
                PredicateSelectedEvent.class,
                e -> {
                    System.out.println("refired PredicateSelectedEvent in TripletView");
                    fireEvent(e);
                });
    }

    public TripletView(
            TripletPresenter presenter, VariableManager variableManager, Triplet triplet) {
        initLayoutFormat();

        createTripletLayout(triplet.getSubject(), triplet.getPredicate(), triplet.getObject());
        createCreateRemoveManagementComponent();
        addListeners();
    }

    public TripletView(TripletPresenter presenter, VariableManager variableManager) {
        initLayoutFormat();
        createTripletLayout();
        createCreateRemoveManagementComponent();
        addListeners();
    }

    // TODO fix code duplication and look for way of default init
    private void createTripletLayout() {

        tripletLayout.setWidthFull();

        createSubjectComponent("Subject");
        createPredicateComponent("Predicate");
        createObjectView("Object");
        add(tripletLayout);
    }

    private void createTripletLayout(Variable subject, Relation relation, ObjectType object) {
        tripletLayout.setWidthFull();

        createSubjectComponent(subject.getName());
        createPredicateComponent(relation.getName());
        createObjectView(object.getName());
        add(tripletLayout);
    }

    private void createSubjectComponent(String initValue) {
        subjectComponent = new SubjectComponent(null); // TODO there once was a var manager
        tripletLayout.add(subjectComponent);
    }

    private void createPredicateComponent(String initValue) {
        predicateComponent = new PredicateComponent(objectView);
        predicateComponent.addListener(PredicateSelectedEvent.class, e -> fireEvent(e));
        tripletLayout.add(predicateComponent);
    }

    private void createObjectView(String initValue) {
        objectView = new ObjectView(null); // TODO there once was a var manager
        tripletLayout.add(objectView);
    }

    private void createCreateRemoveManagementComponent() {
        add(
                new Button(
                        new Icon(VaadinIcon.TRASH),
                        click -> fireEvent(new TripletViewDeleteButtonPressedEvent(this))));
        add(
                new Button(
                        new Icon(VaadinIcon.PLUS),
                        click -> fireEvent(new AddTripletViewAfterButtonPressedEvent(this))));
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
