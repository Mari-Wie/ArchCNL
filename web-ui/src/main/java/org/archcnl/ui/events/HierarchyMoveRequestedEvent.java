package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class HierarchyMoveRequestedEvent extends ComponentEvent<HierarchyView> {
    HierarchyNode dragged;
    HierarchyNode target;
    GridDropLocation location;

    public HierarchyMoveRequestedEvent(
            HierarchyView source,
            boolean fromClient,
            HierarchyNode draggedNode,
            HierarchyNode targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient);
        dragged = draggedNode;
        target = targetNode;
        location = gridDropLocation;
    }

    public HierarchyNode getDraggedNode() {
        return dragged;
    }

    public HierarchyNode getTargetNode() {
        return target;
    }

    public GridDropLocation getGridDropLocation() {
        return location;
    }
}
