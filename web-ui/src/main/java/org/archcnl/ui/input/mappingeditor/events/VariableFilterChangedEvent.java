package org.archcnl.ui.input.mappingeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.mappingeditor.triplet.VariableSelectionComponent;

public class VariableFilterChangedEvent extends ComponentEvent<VariableSelectionComponent> {

    private static final long serialVersionUID = 1209710165251122316L;
    private final String filterString;

    public VariableFilterChangedEvent(
            VariableSelectionComponent source, boolean fromClient, String filterString) {
        super(source, fromClient);
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }
}
