package org.archcnl.ui.common.conceptandrelationlistview.events;

public class ConceptHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent {

    private static final long serialVersionUID = 1L;

    public ConceptHierarchySwapRequestedEvent(HierarchySwapRequestedEvent e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
