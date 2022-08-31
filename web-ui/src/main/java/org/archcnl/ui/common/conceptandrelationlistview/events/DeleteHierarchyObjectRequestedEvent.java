package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class DeleteHierarchyObjectRequestedEvent<T extends HierarchyObject>
        extends ComponentEvent<Component> {

    private static final long serialVersionUID = -8244752047463443057L;
    private HierarchyNode<T> hierarchyNode;

    public DeleteHierarchyObjectRequestedEvent(
            Component component, boolean fromClient, HierarchyNode<T> hierarchyNode) {
        super(component, fromClient);
        this.hierarchyNode = hierarchyNode;
    }

    public HierarchyNode<T> getHierarchyNode() {
        return hierarchyNode;
    }
}
