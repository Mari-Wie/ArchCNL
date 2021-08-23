package org.vaadin.example.queryview;

import com.vaadin.flow.component.ComponentEvent;

public class RemoveWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
    public RemoveWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
