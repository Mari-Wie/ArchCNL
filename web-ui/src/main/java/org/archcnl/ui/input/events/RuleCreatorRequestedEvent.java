package org.archcnl.ui.input.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.ui.input.ruleeditor.ArchitectureRulesLayout;

public class RuleCreatorRequestedEvent extends ComponentEvent<ArchitectureRulesLayout> {

    private static final long serialVersionUID = -3974273159359817201L;

    public RuleCreatorRequestedEvent(ArchitectureRulesLayout source, boolean fromClient) {
        super(source, fromClient);
    }
}
