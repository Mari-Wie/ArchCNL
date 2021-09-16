package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;

import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.domain.input.model.mappings.VariableManager;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.View;

public class SubjectPresenter implements Presenter {

	private static final long serialVersionUID = 7992050926821966999L;
	
	private VariableManager variableManager;

	private View<Presenter> view;
	
	public SubjectPresenter(SubjectContract.View<Presenter> view, VariableManager variableManager) {
		this.view = view;
		this.variableManager = variableManager;
	}

	@Override
	public List<Variable> getVariables() {
		return variableManager.getVariables();
	}

}
