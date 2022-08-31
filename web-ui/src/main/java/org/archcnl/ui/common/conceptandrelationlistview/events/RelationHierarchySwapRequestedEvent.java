package org.archcnl.ui.common.conceptandrelationlistview.events;

import org.archcnl.domain.common.conceptsandrelations.Relation;

public class RelationHierarchySwapRequestedEvent extends HierarchySwapRequestedEvent<Relation> {

    private static final long serialVersionUID = -7059656468211729847L;

    public RelationHierarchySwapRequestedEvent(HierarchySwapRequestedEvent<Relation> e) {
        super(e.getSource(), true, e.getDraggedNode(), e.getTargetNode(), e.getGridDropLocation());
    }
}
