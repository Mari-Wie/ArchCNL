package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class PredicateSelectionEvent extends ComponentEvent<ComboBox<String>> {
    private static final long serialVersionUID = 1L;

    public PredicateSelectionEvent(ComboBox<String>  source, boolean fromClient) {
        super(source, true);
        System.out.println("Event send");
    }
}
