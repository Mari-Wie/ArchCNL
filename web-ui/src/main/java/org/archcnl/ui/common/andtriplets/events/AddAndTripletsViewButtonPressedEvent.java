package org.archcnl.ui.common.andtriplets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.andtriplets.AndTripletsEditorView;

public class AddAndTripletsViewButtonPressedEvent extends ComponentEvent<AndTripletsEditorView> {

    private static final long serialVersionUID = 8182571843065225585L;

    public AddAndTripletsViewButtonPressedEvent(AndTripletsEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
