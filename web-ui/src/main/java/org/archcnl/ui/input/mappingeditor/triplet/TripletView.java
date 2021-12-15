package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.input.mappingeditor.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

public class TripletView extends HorizontalLayout implements TripletContract.View {

    private static final long serialVersionUID = -547117976123681486L;
    private Presenter<View> presenter;
    private Button addButton;
    
    private VariableSelectionView subjectView;
    private ObjectView objectView;
    private PredicateView predicateView;

    public TripletView(Presenter<View> presenter, VariableManager variableManager) {
        this.presenter = presenter;

        setPadding(false);
        setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        setWidthFull();
        SubjectPresenter subjectPresenter = new SubjectPresenter(variableManager);
        presenter.setSubjectPresenter(subjectPresenter);
        subjectView = new VariableSelectionView(subjectPresenter);
        
        ObjectPresenter objectPresenter = new ObjectPresenter(variableManager);
        presenter.setObjectPresenter(objectPresenter);
        objectView = new ObjectView(objectPresenter);
        
        PredicatePresenter predicatePresenter = new PredicatePresenter(objectPresenter);
        presenter.setPredicatePresenter(predicatePresenter);
        predicateView = new PredicateView(predicatePresenter);

        HorizontalLayout triplet = new HorizontalLayout();
        triplet.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        triplet.setWidthFull();
        triplet.add(subjectView);
        triplet.add(predicateView);
        triplet.add(objectView);
        add(triplet);
        add(new Button(new Icon(VaadinIcon.TRASH), click -> presenter.deleteButtonPressed()));
        add(new Button(new Icon(VaadinIcon.PLUS), click -> presenter.addButtonPressed()));
        this.presenter.setView(this);
    }

    @Override
    public void setAddButtonVisible(boolean visible) {
        if (visible) {
            add(addButton);
        } else {
            remove(addButton);
        }
    }

    public Presenter<View> getPresenter() {
        return presenter;
    }
    
    public void setLabels(boolean firstRow)
    {
        if(firstRow)
        {
            subjectView.setLabel("Subject");
            objectView.setLabel("Object");
            predicateView.setLabel("Predicate");
        }
        else
        {
            subjectView.setLabel("");
            objectView.setLabel("");
            predicateView.setLabel("");
        }
    }
}
