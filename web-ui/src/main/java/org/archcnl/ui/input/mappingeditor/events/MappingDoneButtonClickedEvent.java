package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.MappingEditorView;

public class MappingDoneButtonClickedEvent extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = 8492883820342596796L;

    public MappingDoneButtonClickedEvent(MappingEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
