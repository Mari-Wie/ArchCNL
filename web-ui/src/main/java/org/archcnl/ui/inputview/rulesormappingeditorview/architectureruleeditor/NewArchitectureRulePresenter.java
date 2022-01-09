package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.InputContract;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.ArchitectureRulesContract.View;

public class NewArchitectureRulePresenter implements ArchitectureRulesContract.Presenter<View> {

    private static final long serialVersionUID = -5047059702637257819L;
    private InputContract.Remote inputRemote;

    public NewArchitectureRulePresenter(InputContract.Remote inputRemote) {
        this.inputRemote = inputRemote;
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
        inputRemote.switchToArchitectureRulesView();
    }
}
