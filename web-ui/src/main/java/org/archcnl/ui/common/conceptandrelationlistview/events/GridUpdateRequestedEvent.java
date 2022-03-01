package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class GridUpdateRequestedEvent extends ComponentEvent<HierarchyView> {

    private static final long serialVersionUID = -3711307621533570697L;

    public GridUpdateRequestedEvent(final HierarchyView source, final boolean fromClient) {
        super(source, fromClient);
    }
}
