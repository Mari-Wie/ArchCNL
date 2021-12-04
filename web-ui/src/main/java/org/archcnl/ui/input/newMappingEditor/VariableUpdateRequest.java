package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class VariableUpdateRequest extends ComponentEvent<ComboBox<String>> {
    private static final long serialVersionUID = 1L;
    VariableList variableContainter;
    String selectedVariable;

    public VariableUpdateRequest(
            ComboBox<String> source,
            boolean fromClient,
            VariableList variableContainer,
            String selectedVariable) {
        super(source, fromClient);
        this.variableContainter = variableContainer;
        this.selectedVariable = selectedVariable;
        System.out.println("Variable UpdateRequest send");
    }

    public VariableList getVariableContainer() {
        return variableContainter;
    }

    public String getSelectedVariable() {
        return selectedVariable;
    }
}
