package org.archcnl.ui.menudialog.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.ui.menudialog.OpenProjectDialog;

public class ShowFreeTextQueryRequestedEvent extends ComponentEvent<OpenProjectDialog> {

    private static final long serialVersionUID = 805720523135417988L;
    private FreeTextQuery query;
    private boolean defaultQueryTab;

    public ShowFreeTextQueryRequestedEvent(
            OpenProjectDialog source,
            boolean fromClient,
            FreeTextQuery query,
            boolean defaultQueryTab) {
        super(source, fromClient);
        this.query = query;
        this.defaultQueryTab = defaultQueryTab;
    }

    public FreeTextQuery getQuery() {
        return query;
    }

    public boolean isDefaultQueryTab() {
        return defaultQueryTab;
    }
}
