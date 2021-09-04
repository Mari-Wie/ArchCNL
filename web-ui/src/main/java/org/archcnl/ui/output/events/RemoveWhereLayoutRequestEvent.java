package org.archcnl.ui.output.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.output.component.WhereTextBoxesLayout;

public class RemoveWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {

    private static final long serialVersionUID = 1L;

    public RemoveWhereLayoutRequestEvent(
            final WhereTextBoxesLayout source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
