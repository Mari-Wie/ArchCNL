package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events;

import com.vaadin.flow.component.ComponentEvent;
import java.util.Optional;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleCreatorPresenter;

public class SaveArchitectureRuleRequestedEvent extends ComponentEvent<RuleCreatorPresenter> {

    private static final long serialVersionUID = 366316698961954929L;
    private ArchitectureRule newRule;
    private Optional<ArchitectureRule> oldRule;

    public SaveArchitectureRuleRequestedEvent(
            RuleCreatorPresenter source,
            boolean fromClient,
            ArchitectureRule newRule,
            Optional<ArchitectureRule> oldRule) {
        super(source, fromClient);
        this.newRule = newRule;
        this.oldRule = oldRule;
    }

    public Optional<ArchitectureRule> getOldRule() {
        return oldRule;
    }

    public ArchitectureRule getNewRule() {
        return newRule;
    }
}
