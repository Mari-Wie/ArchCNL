package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents.DefaultStatementComponent;

public class ShowAndOrStatmentComponentEvent extends ComponentEvent<DefaultStatementComponent> {

    private static final long serialVersionUID = 1L;
    private boolean showAndOrBlock;

    public ShowAndOrStatmentComponentEvent(
            DefaultStatementComponent source, boolean fromClient, boolean showAndOrBlock) {
        super(source, fromClient);
        this.showAndOrBlock = showAndOrBlock;
    }

    public boolean getShowAndOrBlock() {
        return showAndOrBlock;
    }
}
