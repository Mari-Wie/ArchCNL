package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveArchitectureRuleRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;

@Tag("NewArchitectureRulePresenter")
public class NewArchitectureRulePresenter extends Component {

    private static final long serialVersionUID = -5047059702637257819L;
    private NewArchitectureRuleView view;

    public NewArchitectureRulePresenter() {
        view = new NewArchitectureRuleView(Optional.empty());
        addListeners();
    }

    public NewArchitectureRulePresenter(ArchitectureRule rule) {
        view = new NewArchitectureRuleView(Optional.of(rule.toStringRepresentation()));
        addListeners();
    }

    private void addListeners() {
        view.addListener(
                SaveRuleButtonPressedEvent.class,
                event ->
                        fireEvent(
                                new SaveArchitectureRuleRequestedEvent(
                                        this, true, new ArchitectureRule(event.getRuleString()))));
        view.addListener(RuleEditorRequestedEvent.class, this::fireEvent);
    }

    public NewArchitectureRuleView getView() {
        return view;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
