package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.AndTripletsEditorView;

public class DeleteAndTripletsViewRequestedEvent extends ComponentEvent<AndTripletsEditorView> {

    private static final long serialVersionUID = 9161539929585780142L;

    public DeleteAndTripletsViewRequestedEvent(AndTripletsEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
