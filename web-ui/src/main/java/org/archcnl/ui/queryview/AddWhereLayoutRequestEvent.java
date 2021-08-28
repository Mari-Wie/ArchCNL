package org.archcnl.ui.queryview;

import com.vaadin.flow.component.ComponentEvent;

public class AddWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
    public AddWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
