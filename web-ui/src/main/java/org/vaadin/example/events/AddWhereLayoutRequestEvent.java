package org.vaadin.example;

import com.vaadin.flow.component.ComponentEvent;

public class AddWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
    public AddWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
        super(source, fromClient);
        System.out.println("addWhereLayoutRequestEvent Fired");
    }
}
