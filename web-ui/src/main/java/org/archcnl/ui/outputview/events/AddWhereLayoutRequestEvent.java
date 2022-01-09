package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.WhereTextBoxesLayout;

public class AddWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {

    private static final long serialVersionUID = 1L;

    public AddWhereLayoutRequestEvent(final WhereTextBoxesLayout source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
