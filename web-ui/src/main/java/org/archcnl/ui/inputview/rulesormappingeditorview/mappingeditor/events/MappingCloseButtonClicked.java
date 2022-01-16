package org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorView;

public class MappingCloseButtonClicked extends ComponentEvent<MappingEditorView> {

    private static final long serialVersionUID = 8706139502259364986L;

    public MappingCloseButtonClicked(MappingEditorView source, boolean fromClient) {
        super(source, fromClient);
    }
}
