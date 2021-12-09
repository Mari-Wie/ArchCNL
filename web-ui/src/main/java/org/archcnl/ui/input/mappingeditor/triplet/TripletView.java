package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.common.VariableManager;

public class TripletView extends HorizontalLayout {

    private static final long serialVersionUID = -547117976123681486L;

    private SubjectComponent subjectComponent;
    private PredicateComponent predicateComponent;
    private ObjectView objectView;

    // TODO: remove this reference
    private TripletPresenter presenter;

    public TripletView(TripletPresenter presenter, VariableManager variableManager) {
        this.presenter = presenter;
        setPadding(false);
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        setWidthFull();

        subjectComponent = new SubjectComponent(variableManager);
        objectView = new ObjectView(variableManager);
        predicateComponent = new PredicateComponent(objectView);

        HorizontalLayout triplet = new HorizontalLayout();
        triplet.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        triplet.setWidthFull();
        triplet.add(subjectComponent);
        triplet.add(predicateComponent);
        triplet.add(objectView);
        add(triplet);
        add(new Button(new Icon(VaadinIcon.TRASH), click -> presenter.deleteButtonPressed()));
        add(new Button(new Icon(VaadinIcon.PLUS), click -> presenter.addButtonPressed()));
    }

    public SubjectComponent getSubjectComponent() {
        return subjectComponent;
    }

    public PredicateComponent getPredicateComponent() {
        return predicateComponent;
    }

    public ObjectView getObjectView() {
        return objectView;
    }

    public TripletPresenter getPresenter() {
        return presenter;
    }
}
