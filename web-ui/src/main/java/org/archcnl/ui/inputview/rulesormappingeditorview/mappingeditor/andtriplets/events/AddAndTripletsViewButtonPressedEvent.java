package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.andtriplets.AndTripletsEditorView;

public class AddAndTripletsViewButtonPressedEvent extends ComponentEvent<AndTripletsEditorView> {

    private static final long serialVersionUID = 8182571843065225585L;

    public AddAndTripletsViewButtonPressedEvent(AndTripletsEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
