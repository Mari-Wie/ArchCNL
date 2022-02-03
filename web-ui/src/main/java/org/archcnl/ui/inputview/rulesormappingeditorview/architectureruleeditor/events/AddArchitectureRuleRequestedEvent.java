package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.NewArchitectureRulePresenter;

public class AddArchitectureRuleRequestedEvent
        extends ComponentEvent<NewArchitectureRulePresenter> {

    private static final long serialVersionUID = 366316698961954929L;
    private ArchitectureRule rule;

    public AddArchitectureRuleRequestedEvent(
            NewArchitectureRulePresenter source, boolean fromClient, ArchitectureRule rule) {
        super(source, fromClient);
        this.rule = rule;
    }

    public ArchitectureRule getRule() {
        return rule;
    }
}
