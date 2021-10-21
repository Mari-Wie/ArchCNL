package org.archcnl.ui.input.ruleeditor;

import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.input.ruleeditor.ArchitectureRulesContract.View;

public class NewArchitectureRulePresenter implements ArchitectureRulesContract.Presenter<View> {

    private static final long serialVersionUID = -5047059702637257819L;
    private InputView parent;

    public NewArchitectureRulePresenter(InputView parent) {
        this.parent = parent;
    }

    @Override
    public void saveArchitectureRule(String potentialRule) {
        try {
            ArchitectureRule newRule = parseArchitectureRule(potentialRule);
            RulesConceptsAndRelations.getInstance()
                    .getArchitectureRuleManager()
                    .addArchitectureRule(newRule);
        } catch (NoArchitectureRuleException e) {
            e.printStackTrace();
        }
    }

    private ArchitectureRule parseArchitectureRule(String potentialRule)
            throws NoArchitectureRuleException {
        // TODO implement actual parsing
        return new ArchitectureRule(potentialRule);
    }

    @Override
    public void returnToRulesView() {
        parent.switchToArchitectureRulesView();
    }
}
