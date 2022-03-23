package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.components.GridView;

public class RunQueryRequestedEvent extends ComponentEvent<Component> {

    private static final long serialVersionUID = -6294511171612608590L;
    private String query;
    private GridView gridView;

    public RunQueryRequestedEvent(
            Component source, boolean fromClient, String query, GridView gridView) {
        super(source, fromClient);
        this.query = query;
        this.gridView = gridView;
    }

    public String getQuery() {
        return query;
    }

    public GridView getGridView() {
        return gridView;
    }
}
