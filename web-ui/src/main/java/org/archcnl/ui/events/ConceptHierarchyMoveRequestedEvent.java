package org.archcnl.ui.events;

import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class ConceptHierarchyMoveRequestedEvent extends HierarchyMoveRequestedEvent {

    public ConceptHierarchyMoveRequestedEvent(
            HierarchyView source,
            boolean fromClient,
            HierarchyNode draggedNode,
            HierarchyNode targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient, draggedNode, targetNode, gridDropLocation);
    }

    public ConceptHierarchyMoveRequestedEvent(HierarchyMoveRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
