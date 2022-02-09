package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class DeleteHierarchyObjectRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -8244752047463443057L;
    private HierarchyObject hierarchyObject;
    private HierarchyView<HierarchyObject> hierarchyView;

    public DeleteHierarchyObjectRequestedEvent(
            Component component, boolean fromClient, HierarchyObject hierarchyObject) {
        super(component, fromClient);
        this.hierarchyObject = hierarchyObject;
    }

    public HierarchyObject getHierarchyObject() {
        return hierarchyObject;
    }

    public HierarchyView<HierarchyObject> getHierarchyView() {
        return hierarchyView;
    }

    public void setHierarchyView(HierarchyView<HierarchyObject> hierarchyView) {
        this.hierarchyView = hierarchyView;
    }
}
