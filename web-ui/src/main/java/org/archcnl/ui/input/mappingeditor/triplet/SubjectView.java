package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.model.mappings.Variable;
import org.archcnl.ui.input.mappingeditor.triplet.SubjectContract.Presenter;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class SubjectView extends HorizontalLayout implements SubjectContract.View<SubjectContract.Presenter>{

	private static final long serialVersionUID = 3452412403240444015L;
	
	private Presenter presenter;
	
	public SubjectView() {
		ComboBox<Variable> comboBox = new ComboBox<>("Subject");
		comboBox.setItems(presenter.getVariables());
		comboBox.setItemLabelGenerator(Variable::getName);
		add(comboBox);
	}

	@Override
	public void setPresenter(SubjectContract.Presenter presenter) {
		this.presenter = presenter;
	}

}
