package org.vaadin.example;

import com.vaadin.flow.component.ComponentEvent;

public class ResultUpdateEvent extends ComponentEvent<QueryResults> {
    public ResultUpdateEvent(QueryResults source, boolean fromClient) {
        super(source, fromClient);
        //TODO add logger call for event creation
    }
}
