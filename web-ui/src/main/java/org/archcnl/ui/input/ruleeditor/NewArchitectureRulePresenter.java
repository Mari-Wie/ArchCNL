package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import org.archcnl.domain.input.exceptions.NoArchitectureRuleException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.input.events.RuleEditorRequestedEvent;
import org.archcnl.ui.input.ruleeditor.ArchitectureRulesContract.View;

@Tag("NewArchitectureRulePresenter")
public class NewArchitectureRulePresenter extends Component
        implements ArchitectureRulesContract.Presenter<View> {

    private static final long serialVersionUID = -5047059702637257819L;

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
        fireEvent(new RuleEditorRequestedEvent(this, true));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
