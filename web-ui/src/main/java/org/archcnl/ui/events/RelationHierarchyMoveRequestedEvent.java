package org.archcnl.ui.events;

import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class RelationHierarchyMoveRequestedEvent extends HierarchyMoveRequestedEvent {

    public RelationHierarchyMoveRequestedEvent(
            HierarchyView source,
            boolean fromClient,
            HierarchyNode draggedNode,
            HierarchyNode targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient, draggedNode, targetNode, gridDropLocation);
    }

    public RelationHierarchyMoveRequestedEvent(HierarchyMoveRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
