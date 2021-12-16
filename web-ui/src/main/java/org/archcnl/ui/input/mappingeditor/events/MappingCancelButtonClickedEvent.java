package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.MappingEditorView;

public class MappingCancelButtonClickedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = -7968264880803660814L;

    public MappingCancelButtonClickedEvent(MappingEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
