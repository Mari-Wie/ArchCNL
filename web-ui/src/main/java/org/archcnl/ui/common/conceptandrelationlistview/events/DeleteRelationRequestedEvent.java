package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.HierarchyNode;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class DeleteRelationRequestedEvent extends ComponentEvent<HierarchyView<Relation>> {

    private static final long serialVersionUID = 2597979523906513573L;
    private HierarchyNode<Relation> entry;

    public DeleteRelationRequestedEvent(
            HierarchyView<Relation> source, boolean fromClient, HierarchyNode<Relation> relation) {
        super(source, fromClient);
        this.entry = relation;
    }

    public HierarchyNode<Relation> getHierarchyNode() {
        return entry;
    }

    public String getNodeName() {
        return entry.getName();
    }
}
