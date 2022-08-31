package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class HierarchySwapRequestedEvent<T extends HierarchyObject>
        extends ComponentEvent<HierarchyView<T>> {

    private static final long serialVersionUID = 407454874319949811L;
    HierarchyNode<T> dragged;
    HierarchyNode<T> target;
    GridDropLocation location;

    public HierarchySwapRequestedEvent(
            HierarchyView<T> source,
            boolean fromClient,
            HierarchyNode<T> draggedNode,
            HierarchyNode<T> targetNode,
            GridDropLocation gridDropLocation) {
        super(source, fromClient);
        dragged = draggedNode;
        target = targetNode;
        location = gridDropLocation;
    }

    public HierarchyNode<T> getDraggedNode() {
        return dragged;
    }

    public HierarchyNode<T> getTargetNode() {
        return target;
    }

    public GridDropLocation getGridDropLocation() {
        return location;
    }
}
