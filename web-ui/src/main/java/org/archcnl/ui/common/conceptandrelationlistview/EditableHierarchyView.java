package org.archcnl.ui.common.conceptandrelationlistview;

import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.EditorRequestedEvent;

public class EditableHierarchyView<T extends HierarchyObject> extends HierarchyView {
    public EditableHierarchyView() {
        super();
    }

    @Override
    public HierarchyEntryLayout createNewHierarchyEntry(HierarchyNode node) {
        HierarchyEntryLayout<T> newLayout;
        HierarchyEntryLayoutFactory factory = new HierarchyEntryLayoutFactory<T>();
        if (node.hasEntry() && node.getEntry().isEditable()) {
            newLayout = factory.createEditable(node);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
            newLayout.addListener(EditorRequestedEvent.class, this::fireEvent);
        }
        else if(node.isRemoveable()){
            newLayout = factory.createRemovable(node);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
        } else {
            newLayout = factory.createStatic(node);
        }
        return newLayout;
    }
}
