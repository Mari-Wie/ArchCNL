package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class MappingDescriptionFieldChangedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = -4067744967013965072L;
    private String newDescription;

    public MappingDescriptionFieldChangedEvent(
            MappingEditorView source, boolean fromClient, String newDescription) {
        super(source, fromClient);
        this.newDescription = newDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }
}
