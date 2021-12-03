package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class VariableSelectionEvent extends ComponentEvent<ComboBox<String>> {
    private static final long serialVersionUID = 1L;

    private String selectedVariable;

    public VariableSelectionEvent(
            ComboBox<String> source, boolean fromClient, String selectedVariable) {
        super(source, fromClient);
        System.out.print("VariableSelectionEvent send");
        this.selectedVariable = selectedVariable;
    }

    String getSelectedVariableString() {
        return selectedVariable;
    }
}
