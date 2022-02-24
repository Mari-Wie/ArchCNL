package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleComponent;

public class EditRuleButtonPressedEvent extends ComponentEvent<RuleComponent> {

    private static final long serialVersionUID = 2271818570042087517L;
    private ArchitectureRule rule;

    public EditRuleButtonPressedEvent(
            RuleComponent source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
