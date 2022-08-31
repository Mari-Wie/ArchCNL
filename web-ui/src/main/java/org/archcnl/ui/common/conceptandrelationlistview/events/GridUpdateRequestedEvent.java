package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;

public class GridUpdateRequestedEvent
        extends ComponentEvent<HierarchyView<? extends HierarchyObject>> {

    private static final long serialVersionUID = -3711307621533570697L;

    public GridUpdateRequestedEvent(
            final HierarchyView<? extends HierarchyObject> source, final boolean fromClient) {
        super(source, fromClient);
    }
}
