package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyEntryLayout;

public class EditorRequestedEvent extends ComponentEvent<HierarchyEntryLayout> {

    public EditorRequestedEvent(HierarchyEntryLayout source, boolean isFromClient) {
        super(source, isFromClient);
    }
}
