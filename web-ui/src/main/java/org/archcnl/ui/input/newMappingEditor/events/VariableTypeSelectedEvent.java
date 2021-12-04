package org.archcnl.ui.input.newMappingEditor.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class VariableTypeSelectedEvent extends ComponentEvent<ComboBox<String>> {
    /** */
    private static final long serialVersionUID = 1L;

    ComboBox<String> objectComboBox;

    public VariableTypeSelectedEvent(
            ComboBox<String> source, ComboBox<String> objectComboBox, boolean fromClient) {
        super(source, fromClient);
        this.objectComboBox = objectComboBox;
    }

    ComboBox<String> getTargetObjectComboBox() {
        return objectComboBox;
    }
}
