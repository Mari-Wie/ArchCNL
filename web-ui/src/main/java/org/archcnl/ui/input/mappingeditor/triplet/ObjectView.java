package org.archcnl.ui.input.mappingeditor.triplet;

import org.archcnl.domain.input.model.mappings.ObjectType;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.Presenter;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

import com.vaadin.flow.component.combobox.ComboBox;

public class ObjectView extends ComboBox<ObjectType> implements ObjectContract.View {

	private static final long serialVersionUID = -1105253743414019620L;
	private Presenter<View> presenter;

	public ObjectView(ObjectContract.Presenter<View> presenter) {
		this.presenter = presenter;
		this.presenter.setView(this);
	}

}
