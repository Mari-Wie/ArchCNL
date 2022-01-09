package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class MappingCancelButtonClickedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = -7968264880803660814L;

    public MappingCancelButtonClickedEvent(MappingEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
