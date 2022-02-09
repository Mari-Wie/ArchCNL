package org.archcnl.ui.outputview.queryviews.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;

public class PinFreeTextQueryRequestedEvent extends ComponentEvent<FreeTextQueryUiComponent> {

    private static final long serialVersionUID = 7470233750031587105L;
    private String queryName;

    public PinFreeTextQueryRequestedEvent(
            FreeTextQueryUiComponent source, boolean fromClient, String queryName) {
        super(source, fromClient);
        this.queryName = queryName;
    }

    public String getQueryName() {
        return queryName;
    }
}
