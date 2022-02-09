package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleView;

public class DeleteRuleButtonPressedEvent extends ComponentEvent<RuleView> {

    private static final long serialVersionUID = 6088361727035198579L;
    private ArchitectureRule rule;

    public DeleteRuleButtonPressedEvent(
            RuleView source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
