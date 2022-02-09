package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.ui.menudialog.OpenProjectDialog;

public class ShowCustomQueryRequestedEvent extends ComponentEvent<OpenProjectDialog> {

    private static final long serialVersionUID = 805720523135417988L;
    private Query query;
    private boolean defaultQueryTab;

    public ShowCustomQueryRequestedEvent(
            OpenProjectDialog source, boolean fromClient, Query query, boolean defaultQueryTab) {
        super(source, fromClient);
        this.query = query;
        this.defaultQueryTab = defaultQueryTab;
    }

    public Query getQuery() {
        return query;
    }

    public boolean isDefaultQueryTab() {
        return defaultQueryTab;
    }
}
