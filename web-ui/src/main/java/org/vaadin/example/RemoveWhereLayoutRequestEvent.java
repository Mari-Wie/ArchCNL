package org.vaadin.example;
import com.vaadin.flow.component.ComponentEvent;

public class RemoveWhereLayoutRequestEvent extends ComponentEvent<WhereTextBoxesLayout> {
    public RemoveWhereLayoutRequestEvent(WhereTextBoxesLayout source, boolean fromClient) {
        super(source, fromClient);
        System.out.println("removeWhereLayoutRequestEvent Fired");
    }
}
