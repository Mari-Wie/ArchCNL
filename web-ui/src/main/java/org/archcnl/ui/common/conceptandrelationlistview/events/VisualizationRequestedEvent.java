package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyEntryLayout;

public class VisualizationRequestedEvent
        extends ComponentEvent<HierarchyEntryLayout<? extends HierarchyObject>> {

    private static final long serialVersionUID = 176626025305616188L;

    public VisualizationRequestedEvent(
            HierarchyEntryLayout<? extends HierarchyObject> source, boolean fromClient) {
        super(source, fromClient);
    }
}
