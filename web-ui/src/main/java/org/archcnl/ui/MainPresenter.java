package org.archcnl.ui;

import com.complexible.stardog.StardogException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.io.IOException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.common.ArchitectureCheck;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.HierarchyManager;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.events.EditOptionRequestedEvent;
import org.archcnl.ui.events.FooterOptionRequestedEvent;
import org.archcnl.ui.events.HelpOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.events.RulesOptionRequestedEvent;
import org.archcnl.ui.events.ViewOptionRequestedEvent;
import org.archcnl.ui.inputview.InputPresenter;
import org.archcnl.ui.inputview.presets.PresetsDialogPresenter;
import org.archcnl.ui.inputview.presets.events.UpdateRulesConceptsAndRelationsRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.DeleteRuleButtonPressedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.events.SaveArchitectureRuleRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.AddCustomConceptRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.concepteditor.events.ChangeConceptNameRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.AddCustomRelationRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.mappingeditor.relationeditor.events.ChangeRelationNameRequestedEvent;
import org.archcnl.ui.menudialog.SelectDirectoryDialog;
import org.archcnl.ui.menudialog.events.QuickOutputViewAccessRequestedEvent;
import org.archcnl.ui.menudialog.events.RunToolchainRequestedEvent;
import org.archcnl.ui.outputview.OutputPresenter;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;

@Tag("MainPresenter")
public class MainPresenter extends Component {

    private static final long serialVersionUID = -8850076288722393209L;
    private static final Logger LOG = LogManager.getLogger(MainPresenter.class);
    private final MainView view;
    private final OutputPresenter outputPresenter;
    private final InputPresenter inputPresenter;
    private final ProjectManager projectManager;
    private final ArchitectureRuleManager ruleManager;
    private final ConceptManager conceptManager;
    private final RelationManager relationManager;

    public MainPresenter() throws ConceptDoesNotExistException {
        projectManager = new ProjectManager();
        ruleManager = new ArchitectureRuleManager();
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);

        inputPresenter = new InputPresenter();
        outputPresenter = new OutputPresenter();
        view = new MainView(inputPresenter.getView());

        addViewListeners();
        addInputListeners();
        addOutputListeners();
    }

    private void updateHierarchies(HierarchyManager hierarchyManager, HierarchyView hv) {
        hv.setRoots(hierarchyManager.getRoots());
        hv.update();
    }

    private void selectPathForChecking() {
        SelectDirectoryDialog directoryDialog = new SelectDirectoryDialog();
        directoryDialog.addListener(
                RunToolchainRequestedEvent.class,
                event -> showOutputView(Optional.of(event.getSelectedPath())));
        directoryDialog.addListener(
                QuickOutputViewAccessRequestedEvent.class,
                event -> showOutputView(Optional.empty()));
        directoryDialog.open();
    }

    private void showOutputView(Optional<String> path) {
        try {
            ArchitectureCheck architectureCheck = new ArchitectureCheck();
            outputPresenter.setResultRepository(architectureCheck.getRepository());
            if (path.isPresent()) {
                runArchCnlToolchain(architectureCheck, path.get());
            }
            outputPresenter.displayResult(
                    architectureCheck
                            .getRepository()
                            .executeNativeSelectQuery(QueryUtils.getDefaultQuery()));
            view.showContent(outputPresenter.getView());
            view.setOpenProjectMenuItemEnabled(false);
        } catch (PropertyNotFoundException e2) {
            view.showErrorMessage("Failed to connect to stardog database.");
        } catch (StardogException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Could not connect to the database.");
        } catch (Exception e) {
            view.showErrorMessage(
                    "An unexpected error occured while running the architecture check.");
        }
    }

    private void runArchCnlToolchain(ArchitectureCheck check, String path) {
        try {
            check.runToolchain(path, ruleManager, conceptManager, relationManager);
        } catch (StardogException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Could not connect to the database.");
        } catch (IOException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Rules could not be generated.");
        } catch (PropertyNotFoundException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Properties of database could not be read.");
        } catch (Exception e) {
            view.showErrorMessage(
                    "An unexpected error occured while running the architecture check.");
        }
    }

    private void handleEvent(final ConceptGridUpdateRequestedEvent event) {
        updateHierarchies(conceptManager, event.getSource());
    }

    private void handleEvent(final RelationGridUpdateRequestedEvent event) {
        updateHierarchies(relationManager, event.getSource());
    }

    private void handleEvent(final ConceptHierarchySwapRequestedEvent event) {
        conceptManager.moveNode(event.getDraggedNode(), event.getTargetNode());
        updateHierarchies(conceptManager, event.getSource());
    }

    private void handleEvent(final RelationHierarchySwapRequestedEvent event) {
        relationManager.moveNode(event.getDraggedNode(), event.getTargetNode());
        updateHierarchies(relationManager, event.getSource());
    }

    private void handleEvent(final NodeAddRequestedEvent e) {
        boolean removable = true;
        if (e.nodeType() == NodeAddRequestedEvent.NodeType.CONCEPT) {
            conceptManager.addHierarchyRoot(e.getName(), removable);
            updateHierarchies(conceptManager, e.getSource());
        }
        if (e.nodeType() == NodeAddRequestedEvent.NodeType.RELATION) {
            relationManager.addHierarchyRoot(e.getName(), removable);
            updateHierarchies(relationManager, e.getSource());
        }
    }

    private void handleEvent(final DeleteRuleButtonPressedEvent event) {
        ruleManager.deleteArchitectureRule(event.getRule());
        inputPresenter.updateArchitectureRulesLayout(ruleManager.getArchitectureRules());
    }

    private void handleEvent(final DeleteConceptRequestedEvent event) {
        conceptManager.removeNode(event.getConcept());
        updateHierarchies(conceptManager, event.getSource());
    }

    private void handleEvent(final DeleteRelationRequestedEvent event) {
        relationManager.removeNode(event.getRelation());
        updateHierarchies(relationManager, event.getSource());
    }

    private void handleEvent(SaveArchitectureRuleRequestedEvent event) {
        Optional<ArchitectureRule> oldRule = event.getOldRule();
        if (oldRule.isEmpty()) {
            ruleManager.addArchitectureRule(event.getNewRule());
        } else {
            ruleManager.updateArchitectureRule(oldRule.get(), event.getNewRule());
        }
        inputPresenter.updateArchitectureRulesLayout(ruleManager.getArchitectureRules());
    }

    private void handleEvent(RulesOptionRequestedEvent event) {
        switch (event.getOption()) {
            case IMPORT_FROM_FILE:
                MainPresenter.LOG.warn("{} is not implemented", event.getOption());
                break;
            case IMPORT_RULE_PRESETS:
                PresetsDialogPresenter presenter =
                        new PresetsDialogPresenter(conceptManager, relationManager, ruleManager);

                // listen to Update-Events so that Concepts/Relations/Rules that are
                // created from presets are also added to the UI
                presenter.addListener(
                        UpdateRulesConceptsAndRelationsRequestedEvent.class, this::handleEvent);
                // open dialog
                presenter.getView().open();
                break;
            default:
                MainPresenter.LOG.warn(
                        "Unhandled RulesOption {} appeared in RulesOptionRequestedEvent.",
                        event.getOption());
                break;
        }
    }

    private void handleEvent(UpdateRulesConceptsAndRelationsRequestedEvent event) {
        inputPresenter.getView().updateConceptAndRelations();
        inputPresenter.updateArchitectureRulesLayout(ruleManager.getArchitectureRules());
    }

    private void addViewListeners() {
        view.addListener(
                ViewOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("ViewOptionRequested is not implemented"));
        view.addListener(
                HelpOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("HelpOptionRequested is not implemented"));
        view.addListener(
                ProjectOptionRequestedEvent.class,
                e ->
                        e.handleEvent(
                                view,
                                projectManager,
                                ruleManager,
                                conceptManager,
                                relationManager,
                                outputPresenter,
                                inputPresenter));
        view.addListener(EditOptionRequestedEvent.class, EditOptionRequestedEvent::handleEvent);
        view.addListener(RulesOptionRequestedEvent.class, this::handleEvent);
        view.addListener(FooterOptionRequestedEvent.class, FooterOptionRequestedEvent::handleEvent);
    }

    private void addInputListeners() {

        inputPresenter.addListener(NodeAddRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(OutputViewRequestedEvent.class, e -> selectPathForChecking());
        inputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(SaveArchitectureRuleRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(DeleteRuleButtonPressedEvent.class, this::handleEvent);
        inputPresenter.addListener(DeleteConceptRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(DeleteRelationRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(
                ChangeConceptNameRequestedEvent.class, e -> e.handleEvent(conceptManager));
        inputPresenter.addListener(
                AddCustomConceptRequestedEvent.class, e -> e.handleEvent(conceptManager));
        inputPresenter.addListener(
                ChangeRelationNameRequestedEvent.class, e -> e.handleEvent(relationManager));
        inputPresenter.addListener(
                AddCustomRelationRequestedEvent.class, e -> e.handleEvent(relationManager));
        inputPresenter.addListener(
                PredicateSelectedEvent.class, event -> event.handleEvent(relationManager));
        inputPresenter.addListener(
                RelationListUpdateRequestedEvent.class,
                event -> event.handleEvent(relationManager.getInputRelations()));
        inputPresenter.addListener(
                ConceptListUpdateRequestedEvent.class,
                event -> event.handleEvent(conceptManager.getInputConcepts()));
        inputPresenter.addListener(
                ConceptSelectedEvent.class, event -> event.handleEvent(conceptManager));
    }

    private void addOutputListeners() {

        outputPresenter.addListener(NodeAddRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(
                InputViewRequestedEvent.class,
                e -> {
                    view.showContent(inputPresenter.getView());
                    view.setOpenProjectMenuItemEnabled(true);
                });
        outputPresenter.addListener(
                PredicateSelectedEvent.class, event -> event.handleEvent(relationManager));
        outputPresenter.addListener(
                RelationListUpdateRequestedEvent.class,
                event -> event.handleEvent(relationManager.getOutputRelations()));
        outputPresenter.addListener(
                ConceptListUpdateRequestedEvent.class,
                event -> event.handleEvent(conceptManager.getOutputConcepts()));
        outputPresenter.addListener(
                ConceptSelectedEvent.class, event -> event.handleEvent(conceptManager));
    }

    public MainView getView() {
        return view;
    }
}
