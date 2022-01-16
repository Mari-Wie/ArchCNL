package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
<<<<<<< master:web-ui/src/main/java/org/archcnl/ui/outputview/events/ResultUpdateEvent.java
import org.archcnl.ui.outputview.components.AbstractQueryResultsComponent;
=======
>>>>>>> output model integration mostly finished:web-ui/src/main/java/org/archcnl/ui/output/events/ResultUpdateEvent.java

public class ResultUpdateEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = 1L;

    public ResultUpdateEvent(final Component source, final boolean fromClient) {
        super(source, fromClient);
        // TODO add logger call for event creation
    }
}
