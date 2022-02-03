package org.archcnl.ui.common.conceptandrelationlistview;

import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.ConceptAndRelation;
import org.archcnl.ui.events.EditorRequestedEvent;

public class EditableHierarchyView<T extends ConceptAndRelation> extends HierarchyView {
    public EditableHierarchyView() {
        super();
    }

    @Override
    public HierarchyEntryLayout createNewHierarchyEntry(HierarchyNode node) {
        HierarchyEntryLayout<T> newLayout;
        if (node.hasEntry() && node.getEntry().isEditable()) {
            newLayout = new EditableHierarchyEntryLayout<T>(node);
            newLayout.addListener(EditorRequestedEvent.class, this::fireEvent);
        } else {
            newLayout = new HierarchyEntryLayout<T>(node);
        }
        return newLayout;
    }
}
