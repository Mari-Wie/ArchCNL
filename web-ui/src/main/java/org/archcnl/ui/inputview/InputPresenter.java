package org.archcnl.ui.inputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationEditorRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RuleCreatorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.RulesWidget;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DeleteRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.EditRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveArchitectureRuleRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleCreatorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RulesWidgetRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.MappingEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.ConceptEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.AddCustomConceptRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.ChangeConceptNameRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.RelationEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.AddCustomRelationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.ChangeRelationNameRequestedEvent;

@Tag("InputPresenter")
public class InputPresenter extends Component {

    private static final long serialVersionUID = -3190548402023632606L;
    private InputView view;
    private RulesWidget architectureRulesLayout;

    public InputPresenter() {
        architectureRulesLayout = new RulesWidget();
        architectureRulesLayout.setClassName("architecture-rules");
        view = new InputView(architectureRulesLayout);
        addListeners();
    }

    private void addListeners() {

        view.addListener(NodeAddRequestedEvent.class, this::fireEvent);
        view.addListener(ConceptEditorRequestedEvent.class, this::handleEvent);
        view.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        view.addListener(ConceptHierarchySwapRequestedEvent.class, this::fireEvent);

        view.addListener(RelationEditorRequestedEvent.class, this::handleEvent);
        view.addListener(RelationGridUpdateRequestedEvent.class, this::fireEvent);
        view.addListener(RelationHierarchySwapRequestedEvent.class, this::fireEvent);

        view.addListener(DeleteConceptRequestedEvent.class, this::fireEvent);
        view.addListener(DeleteRelationRequestedEvent.class, this::fireEvent);
        view.addListener(OutputViewRequestedEvent.class, this::fireEvent);
        architectureRulesLayout.addListener(RuleCreatorRequestedEvent.class, this::handleEvent);
        architectureRulesLayout.addListener(EditRuleButtonPressedEvent.class, this::handleEvent);
        architectureRulesLayout.addListener(DeleteRuleButtonPressedEvent.class, this::fireEvent);
    }

    public InputView getView() {
        return view;
    }

    private void handleEvent(final ConceptEditorRequestedEvent event) {
        Optional<CustomConcept> concept = event.getConcept();
        ConceptEditorPresenter conceptEditorPresenter = new ConceptEditorPresenter();
        addListenersToMappingEditor(conceptEditorPresenter);
        conceptEditorPresenter.addListener(ChangeConceptNameRequestedEvent.class, this::fireEvent);
        conceptEditorPresenter.addListener(AddCustomConceptRequestedEvent.class, this::fireEvent);
        if (concept.isPresent()) {
            conceptEditorPresenter.showConcept(concept.get());
        }
        view.changeCurrentlyShownView(conceptEditorPresenter.getMappingEditorView());
    }

    private void handleEvent(final RelationEditorRequestedEvent event) {
        Optional<CustomRelation> relation = event.getRelation();
        RelationEditorPresenter relationEditorPresenter = new RelationEditorPresenter();
        addListenersToMappingEditor(relationEditorPresenter);
        relationEditorPresenter.addListener(
                ChangeRelationNameRequestedEvent.class, this::fireEvent);
        relationEditorPresenter.addListener(AddCustomRelationRequestedEvent.class, this::fireEvent);
        if (relation.isPresent()) {
            relationEditorPresenter.showRelation(relation.get());
        }
        view.changeCurrentlyShownView(relationEditorPresenter.getMappingEditorView());
    }

    private void handleEvent(final RulesWidgetRequestedEvent event) {
        view.changeCurrentlyShownView(architectureRulesLayout);
    }

    private void handleEvent(final RuleCreatorRequestedEvent event) {
        // TODO move this to the InputPresenter
        final RuleCreatorPresenter presenter = new RuleCreatorPresenter();
        presenter.addListener(RulesWidgetRequestedEvent.class, this::handleEvent);
        presenter.addListener(SaveArchitectureRuleRequestedEvent.class, this::fireEvent);
        presenter.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        presenter.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        view.changeCurrentlyShownView(presenter.getView());
    }

    private void handleEvent(final EditRuleButtonPressedEvent event) {
        final RuleCreatorPresenter presenter = new RuleCreatorPresenter(event.getRule());
        presenter.addListener(RulesWidgetRequestedEvent.class, this::handleEvent);
        presenter.addListener(SaveArchitectureRuleRequestedEvent.class, this::fireEvent);
        view.changeCurrentlyShownView(presenter.getView());
    }

    public void updateArchitectureRulesLayout(final List<ArchitectureRule> rules) {
        architectureRulesLayout.updateRules(rules);
    }

    public void updateConceptAndRelations() {
        view.updateConceptAndRelations();
    }

    private void addListenersToMappingEditor(MappingEditorPresenter presenter) {
        presenter.addListener(RulesWidgetRequestedEvent.class, this::handleEvent);
        presenter.addListener(PredicateSelectedEvent.class, this::fireEvent);
        presenter.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
        presenter.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        presenter.addListener(ConceptSelectedEvent.class, this::fireEvent);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
