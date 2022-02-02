package org.archcnl.ui.inputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.ArchitectureRulesLayout;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.NewArchitectureRulePresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.NewArchitectureRuleView;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.ConceptEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RelationEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleCreatorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.RuleEditorRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.ConceptEditorPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.RelationEditorPresenter;

@Tag("InputPresenter")
public class InputPresenter extends Component {

    private static final long serialVersionUID = -3190548402023632606L;
    private InputView view;
    private ArchitectureRulesLayout architectureRulesLayout;

    public InputPresenter() {
        architectureRulesLayout = new ArchitectureRulesLayout();
        architectureRulesLayout.setClassName("architecture-rules");
        view = new InputView(architectureRulesLayout);
        addListeners();
    }

    private void addListeners() {
        view.addListener(ConceptEditorRequestedEvent.class, this::handleEvent);
        view.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        view.addListener(ConceptHierarchySwapRequestedEvent.class, this::fireEvent);

        view.addListener(RelationEditorRequestedEvent.class, this::handleEvent);
        view.addListener(RelationGridUpdateRequestedEvent.class, this::fireEvent);
        view.addListener(RelationHierarchySwapRequestedEvent.class, this::fireEvent);

        view.addListener(OutputViewRequestedEvent.class, this::fireEvent);
        architectureRulesLayout.addListener(RuleCreatorRequestedEvent.class, this::handleEvent);
    }

    public InputView getView() {
        return view;
    }

    //  public void handleEvent(final ConceptHierarchySwapRequestedEvent event) {
    //      ConceptManager cm = RulesConceptsAndRelations.getInstance().getConceptManager();
    //      cm.moveNode(event.getDraggedNode(), event.getTargetNode());
    //      event.getSource().clearRoots();
    //      for (HierarchyNode<Concept> node :  cm.getRoots()){
    //          event.getSource().addRoot(node);
    //      }
    //      event.getSource().update();
    //      System.out.println("Input presenter received ConceptHierarchySwapRequestedEvent");
    //  }
    //  public void handleEvent(final RelationHierarchySwapRequestedEvent event) {
    //      //TODO: add relation things (see handleEvent(final ConceptHierarchySwapRequestedEvent
    // event));
    //      System.out.println("Input presenter received RelationHierarchySwapRequestedEvent");
    //  }

    //  public void handleEvent(final ConceptGridUpdateRequestedEvent event) {
    //      System.out.println("Input presenter received ConceptGridUpdateRequest");
    //      ConceptManager cm = RulesConceptsAndRelations.getInstance().getConceptManager();
    //      event.getSource().clearRoots();
    //      for (HierarchyNode<Concept> node :  cm.getRoots()){
    //          event.getSource().addRoot(node);
    //      }
    //      event.getSource().update();
    //  }

    //  public void handleEvent(final RelationGridUpdateRequestedEvent event) {
    //      //TODO: fix this (see function handleEvent(final ConceptGridUpdateRequestedEvent event))
    //      System.out.println("Input presenter received RelationGridUpdateRequest");
    //      RelationManager cm = RulesConceptsAndRelations.getInstance().getRelationManager();
    //      List<Relation> c = cm.getInputRelations();
    //      List<CustomRelation> cc = cm.getCustomRelations();
    //      HierarchyNode<Relation> defaultRelations = new HierarchyNode<Relation>("Default
    // Relations");
    //      for (Relation c_loop : c) {
    //          defaultRelations.add(c_loop);
    //      }
    //      HierarchyNode<Relation> customRelations = new HierarchyNode<Relation>("Custom
    // Relation");
    //      for (Relation c_loop : cc) {
    //          customRelations.add(c_loop);
    //      }

    //      event.getSource().clearRoots();
    //      event.getSource().addRoot(defaultRelations);
    //      event.getSource().addRoot(customRelations);
    //      event.getSource().update();
    //  }

    public void handleEvent(final ConceptEditorRequestedEvent event) {
        ConceptEditorPresenter conceptEditorPresenter;
        if (event.getConcept().isPresent()) {
            conceptEditorPresenter = new ConceptEditorPresenter(event.getConcept().get());
        } else {
            conceptEditorPresenter = new ConceptEditorPresenter();
        }
        conceptEditorPresenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        view.changeCurrentlyShownView(conceptEditorPresenter.getMappingEditorView());
    }

    public void handleEvent(final RelationEditorRequestedEvent event) {
        RelationEditorPresenter relationEditorPresenter;
        if (event.getRelation().isPresent()) {
            relationEditorPresenter = new RelationEditorPresenter(event.getRelation().get());
        } else {
            relationEditorPresenter = new RelationEditorPresenter();
        }
        relationEditorPresenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        view.changeCurrentlyShownView(relationEditorPresenter.getMappingEditorView());
    }

    public void handleEvent(final RuleEditorRequestedEvent event) {
        view.changeCurrentlyShownView(architectureRulesLayout);
    }

    public void handleEvent(final RuleCreatorRequestedEvent event) {
        final NewArchitectureRulePresenter presenter = new NewArchitectureRulePresenter();
        presenter.addListener(RuleEditorRequestedEvent.class, this::handleEvent);
        final NewArchitectureRuleView newArchitectureRuleView =
                new NewArchitectureRuleView(presenter);
        view.changeCurrentlyShownView(newArchitectureRuleView);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
