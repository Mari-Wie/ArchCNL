package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.HierarchyNode;

public class DeleteHierarchyObjectRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -8244752047463443057L;
    private HierarchyNode hierarchyNode;

    public DeleteHierarchyObjectRequestedEvent(
            Component component, boolean fromClient, HierarchyNode hierarchyNode) {
        super(component, fromClient);
        this.hierarchyNode = hierarchyNode;
    }

    public HierarchyNode getHierarchyObject() {
        return hierarchyNode;
    }
}
