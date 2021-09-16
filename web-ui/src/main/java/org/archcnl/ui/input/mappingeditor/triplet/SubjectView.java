package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.View;

import com.vaadin.flow.component.combobox.ComboBox;

public class SubjectView extends ComboBox<Variable> implements SubjectContract.View {

	private static final long serialVersionUID = 3452412403240444015L;
	
	private Presenter<View> presenter;
	
	public SubjectView(SubjectContract.Presenter<View> presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
		setLabel("Subject");
		setPlaceholder("Variable");
		setItems(presenter.getVariables());
		setItemLabelGenerator(Variable::getName);
	}

}
