package org.vaadin.example;
import com.vaadin.flow.component.ComponentEvent;

public class ResultUpdateEvent extends ComponentEvent<QueryResults> {
    public ResultUpdateEvent(QueryResults source, boolean fromClient) {
        super(source, fromClient);
        System.out.println("Result Fired");
    }
}
