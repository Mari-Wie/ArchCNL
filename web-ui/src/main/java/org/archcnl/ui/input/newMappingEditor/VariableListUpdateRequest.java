package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;

public class VariableListUpdateRequest extends ComponentEvent<ComboBox<String>> {
    private static final long serialVersionUID = 1L;

    public VariableListUpdateRequest(ComboBox<String> source, boolean fromClient) {
        super(source, fromClient);
        System.out.print("VariableListUpdateRequestSend send");
    }
}
