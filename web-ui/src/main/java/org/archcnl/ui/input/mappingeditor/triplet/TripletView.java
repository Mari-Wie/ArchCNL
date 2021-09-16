package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.ui.input.mappingeditor.MappingEditorContract;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TripletView extends HorizontalLayout implements TripletContract.View {

	private static final long serialVersionUID = -547117976123681486L;

	private Presenter<View> presenter;
	
	public TripletView(Presenter<View> presenter, MappingEditorContract.Presenter<MappingEditorContract.View> superPresenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		
		SubjectPresenter subjectPresenter = new SubjectPresenter(superPresenter);
		presenter.setSubjectPresenter(subjectPresenter);
		SubjectView subjectView = new SubjectView(subjectPresenter);
		PredicatePresenter predicatePresenter = new PredicatePresenter();
		presenter.setPredicatePresenter(predicatePresenter);
		PredicateView predicateView = new PredicateView(predicatePresenter);
		ObjectPresenter objectPresenter = new ObjectPresenter();
		presenter.setObjectPresenter(objectPresenter);
		ObjectView objectView = new ObjectView(objectPresenter);
		
		add(subjectView);
		add(predicateView);
		add(objectView);
	}

}
