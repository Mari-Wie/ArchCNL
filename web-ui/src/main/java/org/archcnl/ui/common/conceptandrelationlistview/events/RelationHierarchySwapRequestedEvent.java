package org.archcnl.ui.common.conceptandrelationlistview.events;

public class RelationHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent {

    private static final long serialVersionUID = -7059656468211729847L;

    public RelationHierarchySwapRequestedEvent(HierarchySwapRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
