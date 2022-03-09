package org.archcnl.ui.inputview.rulesormappingeditorview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RulesWidget;

public class RuleCreatorRequestedEvent extends ComponentEvent<RulesWidget> {

    private static final long serialVersionUID = -3974273159359817201L;

    public RuleCreatorRequestedEvent(final RulesWidget source, final boolean fromClient) {
        super(source, fromClient);
    }
}
