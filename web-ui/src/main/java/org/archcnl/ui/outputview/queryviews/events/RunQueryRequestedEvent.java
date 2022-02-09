package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.components.GridView;

public class RunQueryRequestedEvent extends ComponentEvent<CustomQueryPresenter> {

    private static final long serialVersionUID = -6294511171612608590L;
    private Query query;
    private GridView gridView;

    public RunQueryRequestedEvent(
            CustomQueryPresenter source, boolean fromClient, Query query, GridView gridView) {
        super(source, fromClient);
        this.query = query;
        this.gridView = gridView;
    }

    public Query getQuery() {
        return query;
    }

    public GridView getGridView() {
        return gridView;
    }
}
