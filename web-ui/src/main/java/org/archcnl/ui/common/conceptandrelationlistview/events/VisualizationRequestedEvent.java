package org.archcnl.ui.common.conceptandrelationlistview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.common.conceptsandrelations.HierarchyObject;

public class VisualizationRequestedEvent<T extends HierarchyObject>
        extends ComponentEvent<Component> {

    private static final long serialVersionUID = 176626025305616188L;
    private final T entry;

    public VisualizationRequestedEvent(Component source, boolean fromClient, T t) {
        super(source, fromClient);
        this.entry = t;
    }

    public T getEntry() {
        return entry;
    }
}
