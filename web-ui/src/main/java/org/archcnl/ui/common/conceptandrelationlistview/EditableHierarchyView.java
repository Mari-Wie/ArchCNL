package org.archcnl.ui.common.conceptandrelationlistview;

import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteHierarchyObjectRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.EditorRequestedEvent;

public class EditableHierarchyView<T extends HierarchyObject> extends HierarchyView<T> {

    private static final long serialVersionUID = 4409737601179383368L;

    @Override
    public HierarchyEntryLayout<T> createNewHierarchyEntry(HierarchyNode<T> node) {
        HierarchyEntryLayout<T> newLayout;
        HierarchyEntryLayoutFactory<T> factory = new HierarchyEntryLayoutFactory<>();
        if (node.hasEntry() && node.getEntry().isEditable()) {
            newLayout = factory.createEditable(node);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
            newLayout.addListener(EditorRequestedEvent.class, this::fireEvent);
        } else if (node.isRemoveable()) {
            newLayout = factory.createRemovable(node);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
        } else {
            newLayout = factory.createStatic(node);
        }
        return newLayout;
    }
}
