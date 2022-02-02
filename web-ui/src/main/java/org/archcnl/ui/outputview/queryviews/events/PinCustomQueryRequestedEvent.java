package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.CustomQueryView;

public class PinCustomQueryRequestedEvent extends ComponentEvent<CustomQueryPresenter> {

    private static final long serialVersionUID = 7470233750031587105L;
    private String queryName;
    private CustomQueryView linkedView;

    public PinCustomQueryRequestedEvent(
            CustomQueryPresenter source,
            boolean fromClient,
            CustomQueryView linkedView,
            String queryName) {
        super(source, fromClient);
        this.linkedView = linkedView;
        this.queryName = queryName;
    }

    public String getQueryName() {
        return queryName;
    }

    public CustomQueryView getLinkedView() {
        return linkedView;
    }
}
