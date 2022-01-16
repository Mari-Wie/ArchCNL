package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class MappingNameFieldChangedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = 1631291025223246543L;
    private String newName;

    public MappingNameFieldChangedEvent(
            MappingEditorView source, boolean fromClient, String newName) {
        super(source, fromClient);
        this.newName = newName;
    }

    public String getNewName() {
        return newName;
    }
}
