package org.archcnl.ui.input.newMappingEditor.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class PredicateSelectionEvent extends ComponentEvent<ComboBox<String>> {
    private static final long serialVersionUID = 1L;

    String selectedPredicate;
    ComboBox<String> variableTypeSelection;

    public PredicateSelectionEvent(
            ComboBox<String> source,
            ComboBox<String> variableTypeSelection,
            boolean fromClient,
            String selectedPredicate) {
        super(source, true);
        this.variableTypeSelection = variableTypeSelection;
        this.selectedPredicate = selectedPredicate;
        System.out.println("Event send");
    }

    public String getSelectedPredicate() {
        return selectedPredicate;
    }

    public ComboBox<String> getVariableTypeSelection() {
        return variableTypeSelection;
    }
}
