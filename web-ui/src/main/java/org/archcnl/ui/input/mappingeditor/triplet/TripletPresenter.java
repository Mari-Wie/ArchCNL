package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.ui.input.mappingeditor.triplet.TripletContract.View;

public class TripletPresenter implements TripletContract.Presenter<View> {

	private static final long serialVersionUID = -7565399174691810818L;
	private SubjectPresenter subjectPresenter;
	private PredicatePresenter setPredicatePresenter;
	private ObjectPresenter setObjectPresenter;
	private View view;

	@Override
	public void setSubjectPresenter(SubjectPresenter subjectPresenter) {
		this.subjectPresenter = subjectPresenter;
		
	}

	@Override
	public void setPredicatePresenter(PredicatePresenter predicatePresenter) {
		this.setPredicatePresenter = predicatePresenter;
		
	}

	@Override
	public void setObjectPresenter(ObjectPresenter objectPresenter) {
		this.setObjectPresenter = objectPresenter;
		
	}

	@Override
	public void setView(View view) {
		this.view = view;	
	}

}
