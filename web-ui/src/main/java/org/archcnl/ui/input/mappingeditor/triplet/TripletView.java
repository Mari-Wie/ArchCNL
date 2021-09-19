package org.archcnl.ui.input.mappingeditor.triplet;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

public class TripletView extends HorizontalLayout implements TripletContract.View {

    private static final long serialVersionUID = -547117976123681486L;

    private Presenter<View> presenter;

    public TripletView(Presenter<View> presenter, VariableManager variableManager) {
        this.presenter = presenter;
        this.presenter.setView(this);

        SubjectPresenter subjectPresenter = new SubjectPresenter(variableManager);
        presenter.setSubjectPresenter(subjectPresenter);
        SubjectView subjectView = new SubjectView(subjectPresenter);
        ObjectPresenter objectPresenter = new ObjectPresenter();
        presenter.setObjectPresenter(objectPresenter);
        ObjectView objectView = new ObjectView(objectPresenter, variableManager);
        PredicatePresenter predicatePresenter = new PredicatePresenter(objectPresenter);
        presenter.setPredicatePresenter(predicatePresenter);
        PredicateView predicateView = new PredicateView(predicatePresenter);

        add(subjectView);
        add(predicateView);
        add(objectView);
    }
}
