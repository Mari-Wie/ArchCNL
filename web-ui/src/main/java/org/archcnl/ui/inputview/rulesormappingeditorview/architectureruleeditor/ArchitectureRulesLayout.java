package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.RulesOrMappingEditorView;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleCreatorRequestedEvent;

public class ArchitectureRulesLayout extends RulesOrMappingEditorView {

    private static final long serialVersionUID = 1L;

    VerticalLayout rulesLayout = new VerticalLayout();

    public ArchitectureRulesLayout() {
        // Remove style property to makes no sense in this layout
        // TODO: Separate ArchitectureRulesLayout from CreateNewLayout
        createCreateNewLayout(
                "Architecture Rules",
                "Create new Arch Rule",
                e -> fireEvent(new RuleCreatorRequestedEvent(this, true)));
        add(rulesLayout);
        getStyle().set("border", "1px solid black");
    }

    public void updateRules(final List<ArchitectureRule> rules) {
        rulesLayout.removeAll();
        for (int i = 0; i < rules.size(); i++) {
            rulesLayout.add(new RuleView(rules.get(i), i + 1));
        }
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
