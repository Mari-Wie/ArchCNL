package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleView;

public class EditRuleButtonPressedEvent extends ComponentEvent<RuleView> {

    private static final long serialVersionUID = 2271818570042087517L;
    private ArchitectureRule rule;

    public EditRuleButtonPressedEvent(RuleView source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
