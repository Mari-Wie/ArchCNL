package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.ui.outputview.components.GridView;

public class RunButtonPressedEvent extends ComponentEvent<GridView> {

    private static final long serialVersionUID = 2614884942938936225L;
    private Query query;

    public RunButtonPressedEvent(GridView source, boolean fromClient) {
        super(source, fromClient);
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
