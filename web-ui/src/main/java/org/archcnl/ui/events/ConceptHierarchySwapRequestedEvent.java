package org.archcnl.ui.events;

import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class ConceptHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent {

    public ConceptHierarchySwapRequestedEvent(
            HierarchyView source,
            boolean fromClient,
            HierarchyNode draggedNode,
            HierarchyNode targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient, draggedNode, targetNode, gridDropLocation);
    }

    public ConceptHierarchySwapRequestedEvent(HierarchySwapRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
