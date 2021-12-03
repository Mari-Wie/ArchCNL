package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class PredicateListUpdateRequest extends ComponentEvent<ComboBox<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PredicateListUpdateRequest(ComboBox<String> source, boolean fromClient) {
		super(source, fromClient);
		System.out.println("PredicateListUpdateRequest send");
	}

}
