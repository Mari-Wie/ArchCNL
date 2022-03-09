package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveArchitectureRuleRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RulesWidgetRequestedEvent;

@Tag("NewArchitectureRulePresenter")
public class RuleCreatorPresenter extends Component {

    private static final long serialVersionUID = -5047059702637257819L;
    private RuleCreatorView view;
    Optional<ArchitectureRule> oldRule;

    public RuleCreatorPresenter() {
        oldRule = Optional.empty();
        view = new RuleCreatorView(Optional.empty());
        view.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        view.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        addListeners();
    }

    public RuleCreatorPresenter(ArchitectureRule rule) {
        oldRule = Optional.of(rule);
        view = new RuleCreatorView(Optional.of(rule.transformToGui()));
        addListeners();
    }

    private void addListeners() {
        view.addListener(
                SaveRuleButtonPressedEvent.class,
                event ->
                        fireEvent(
                                new SaveArchitectureRuleRequestedEvent(
                                        this,
                                        true,
                                        new ArchitectureRule(event.getRuleString()),
                                        oldRule)));
        view.addListener(RulesWidgetRequestedEvent.class, this::fireEvent);
    }

    public RuleCreatorView getView() {
        return view;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
