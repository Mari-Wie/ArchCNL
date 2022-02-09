package org.archcnl.ui.inputview;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;

public class RemoveArchitectureRuleRequestedEvent extends ComponentEvent<InputPresenter> {

    private static final long serialVersionUID = 6880199485628436101L;
    private ArchitectureRule rule;

    public RemoveArchitectureRuleRequestedEvent(
            InputPresenter source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
