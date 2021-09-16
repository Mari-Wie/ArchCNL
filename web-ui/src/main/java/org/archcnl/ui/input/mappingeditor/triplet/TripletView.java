package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.model.mappings.VariableManager;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TripletView extends HorizontalLayout {

	private static final long serialVersionUID = -547117976123681486L;
	
	private VariableManager variableManager;
	
	public TripletView(VariableManager variableManager) {
		this.variableManager = variableManager;
		SubjectView subjectView = new SubjectView();
		new SubjectPresenter(subjectView, variableManager);
	}

}
