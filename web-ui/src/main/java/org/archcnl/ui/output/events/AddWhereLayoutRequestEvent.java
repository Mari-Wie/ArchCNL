package org.archcnl.ui.output.events;

import org.archcnl.ui.output.component.WhereTextBoxesLayout;
import com.vaadin.flow.component.ComponentEvent;

public class AddWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {

    private static final long serialVersionUID = 1L;

    public AddWhereLayoutRequestEvent(final WhereTextBoxesLayout source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
