package org.archcnl.ui.common.conceptandrelationlistview.events;

import org.archcnl.domain.common.conceptsandrelations.Concept;

public class ConceptHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent<Concept> {

    private static final long serialVersionUID = 1L;

    public ConceptHierarchySwapRequestedEvent(HierarchySwapRequestedEvent<Concept> e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
