package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class RelationGridUpdateRequestedEvent extends ComponentEvent<HierarchyView<Relation>> {

    private static final long serialVersionUID = -3711307621533570697L;

    public RelationGridUpdateRequestedEvent(
            final HierarchyView<Relation> source, final boolean fromClient) {
        super(source, fromClient);
    }
}
