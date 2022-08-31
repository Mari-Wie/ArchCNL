package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyEntryLayout;

public class EditorRequestedEvent
        extends ComponentEvent<HierarchyEntryLayout<? extends HierarchyObject>> {

    private static final long serialVersionUID = 2452035282766848616L;

    public EditorRequestedEvent(
            HierarchyEntryLayout<? extends HierarchyObject> source, boolean isFromClient) {
        super(source, isFromClient);
    }
}
