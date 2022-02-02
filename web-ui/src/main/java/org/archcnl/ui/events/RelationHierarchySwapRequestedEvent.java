package org.archcnl.ui.events;

import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class RelationHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent {

    public RelationHierarchySwapRequestedEvent(
            HierarchyView source,
            boolean fromClient,
            HierarchyNode draggedNode,
            HierarchyNode targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient, draggedNode, targetNode, gridDropLocation);
    }

    public RelationHierarchySwapRequestedEvent(HierarchySwapRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
