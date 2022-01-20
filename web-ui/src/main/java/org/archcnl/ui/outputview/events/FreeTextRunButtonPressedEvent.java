package org.archcnl.ui.outputview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.outputview.components.GridView;

public class FreeTextRunButtonPressedEvent extends ComponentEvent<GridView> {

    private static final long serialVersionUID = -8751697009102796664L;
    private String query;

    public FreeTextRunButtonPressedEvent(GridView source, boolean fromClient, String query) {
        super(source, fromClient);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
