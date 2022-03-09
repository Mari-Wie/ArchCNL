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
        if (node.hasEntry() && node.getEntry().isEditable()) {
            newLayout = new EditableHierarchyEntryLayout<T>(node);
            newLayout.addListener(EditorRequestedEvent.class, this::fireEvent);
            newLayout.addListener(DeleteHierarchyObjectRequestedEvent.class, this::fireEvent);
        } else {
            newLayout = new HierarchyEntryLayout<T>(node);
        }
        return newLayout;
    }
}
