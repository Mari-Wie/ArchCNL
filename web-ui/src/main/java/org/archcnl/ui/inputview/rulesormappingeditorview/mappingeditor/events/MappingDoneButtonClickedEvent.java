package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class MappingDoneButtonClickedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = 8492883820342596796L;

    public MappingDoneButtonClickedEvent(MappingEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
