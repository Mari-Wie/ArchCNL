package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DeleteRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.EditRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleCreatorRequestedEvent;

public class RulesWidget extends RulesOrMappingEditorView {

    private static final long serialVersionUID = 1L;

    VerticalLayout rulesLayout = new VerticalLayout();

    public RulesWidget() {
        setWidthFull();
        // TODO: Separate ArchitectureRulesLayout from CreateNewLayout
        createEditorButton(
                "Create new Arch Rule", e -> fireEvent(new RuleCreatorRequestedEvent(this, true)));
        add(rulesLayout);
    }

    public void updateRules(final List<ArchitectureRule> rules) {
        rulesLayout.removeAll();
        for (int i = 0; i < rules.size(); i++) {
            RuleComponent ruleView = new RuleComponent(rules.get(i), i + 1);
            ruleView.addListener(EditRuleButtonPressedEvent.class, this::fireEvent);
            ruleView.addListener(DeleteRuleButtonPressedEvent.class, this::fireEvent);
            rulesLayout.add(ruleView);
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
