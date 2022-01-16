package org.archcnl.ui.inputview.rulesormappingeditorview.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.ArchitectureRulesLayout;

public class RuleCreatorRequestedEvent extends ComponentEvent<ArchitectureRulesLayout> {

    private static final long serialVersionUID = -3974273159359817201L;

    public RuleCreatorRequestedEvent(
            final ArchitectureRulesLayout source, final boolean fromClient) {
        super(source, fromClient);
    }
}
