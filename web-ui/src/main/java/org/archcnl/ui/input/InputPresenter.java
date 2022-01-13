package org.archcnl.ui.input;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.input.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.input.events.OutputViewRequestedEvent;
import org.archcnl.ui.input.events.RelationEditorRequestedEvent;
import org.archcnl.ui.input.events.RuleCreatorRequestedEvent;
import org.archcnl.ui.input.events.RuleEditorRequestedEvent;
import org.archcnl.ui.input.mappingeditor.ConceptEditorPresenter;
import org.archcnl.ui.input.mappingeditor.RelationEditorPresenter;
import org.archcnl.ui.input.ruleeditor.ArchitectureRulesLayout;
import org.archcnl.ui.input.ruleeditor.NewArchitectureRulePresenter;
import org.archcnl.ui.input.ruleeditor.NewArchitectureRuleView;

@Tag("InputPresenter")
public class InputPresenter extends Component {

    private static final long serialVersionUID = -3190548402023632606L;
    private InputView view;
    private ArchitectureRulesLayout architectureRulesLayout;

    public InputPresenter() {
        architectureRulesLayout = new ArchitectureRulesLayout();
        view = new InputView(architectureRulesLayout);
        addListeners();
    }

    private void addListeners() {
        view.addListener(ConceptEditorRequestedEvent.class, this::handleEvent);
        view.addListener(RelationEditorRequestedEvent.class, this::handleEvent);
        view.addListener(OutputViewRequestedEvent.class, this::fireEvent);
        architectureRulesLayout.addListener(RuleCreatorRequestedEvent.class, this::handleEvent);
    }

    public InputView getView() {
        return view;
    }

    public void handleEvent(ConceptEditorRequestedEvent event) {
        ConceptEditorPresenter conceptEditorPresenter;
        if (event.getConcept().isPresent()) {
            conceptEditorPresenter = new ConceptEditorPresenter(event.getConcept().get());
        } else {
            conceptEditorPresenter = new ConceptEditorPresenter();
        }
        conceptEditorPresenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        view.changeCurrentlyShownView(conceptEditorPresenter.getMappingEditorView());
    }

    public void handleEvent(RelationEditorRequestedEvent event) {
        RelationEditorPresenter relationEditorPresenter;
        if (event.getRelation().isPresent()) {
            relationEditorPresenter = new RelationEditorPresenter(event.getRelation().get());
        } else {
            relationEditorPresenter = new RelationEditorPresenter();
        }
        relationEditorPresenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        view.changeCurrentlyShownView(relationEditorPresenter.getMappingEditorView());
    }

    public void handleEvent(RuleEditorRequestedEvent event) {
        view.changeCurrentlyShownView(architectureRulesLayout);
    }

    public void handleEvent(RuleCreatorRequestedEvent event) {
        NewArchitectureRulePresenter presenter = new NewArchitectureRulePresenter();
        presenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        NewArchitectureRuleView newArchitectureRuleView = new NewArchitectureRuleView(presenter);
        view.changeCurrentlyShownView(newArchitectureRuleView);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
