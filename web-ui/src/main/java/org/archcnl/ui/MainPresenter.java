package org.archcnl.ui;

import com.complexible.stardog.StardogException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.common.ArchitectureCheck;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.HierarchyManager;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteConceptRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.DeleteRelationRequestedEvent;
import org.archcnl.ui.common.dialogs.ConfirmDialog;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.EditOptionRequestedEvent;
import org.archcnl.ui.events.FooterOptionRequestedEvent;
import org.archcnl.ui.events.HelpOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
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
import org.archcnl.ui.menudialog.OpenProjectDialog;
import org.archcnl.ui.menudialog.SaveProjectDialog;
import org.archcnl.ui.menudialog.SelectDirectoryDialog;
import org.archcnl.ui.menudialog.events.ProjectOpenedEvent;
import org.archcnl.ui.menudialog.events.ProjectSavedEvent;
import org.archcnl.ui.menudialog.events.QuickOutputViewAccessRequestedEvent;
import org.archcnl.ui.menudialog.events.RunToolchainRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowCustomQueryRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowFreeTextQueryRequestedEvent;
import org.archcnl.ui.outputview.OutputView;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;

@Tag("MainPresenter")
public class MainPresenter extends Component {

    private static final long serialVersionUID = -8850076288722393209L;
    private static final Logger LOG = LogManager.getLogger(MainPresenter.class);
    private final MainView view;
    private final OutputView outputView;
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
        inputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(SaveArchitectureRuleRequestedEvent.class, this::handleEvent);
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

        outputView = new OutputView();
        outputView.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        outputView.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        outputView.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        outputView.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);
        outputView.addListener(
                PredicateSelectedEvent.class, event -> event.handleEvent(relationManager));
        outputView.addListener(
                RelationListUpdateRequestedEvent.class,
                event -> event.handleEvent(relationManager.getOutputRelations()));
        outputView.addListener(
                ConceptListUpdateRequestedEvent.class,
                event -> event.handleEvent(conceptManager.getOutputConcepts()));
        outputView.addListener(
                ConceptSelectedEvent.class, event -> event.handleEvent(conceptManager));
        inputPresenter.addListener(DeleteRuleButtonPressedEvent.class, this::handleEvent);
        inputPresenter.addListener(DeleteConceptRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(DeleteRelationRequestedEvent.class, this::handleEvent);

        view = new MainView(inputPresenter.getView());

        addListeners();
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

    private void handleEvent(final DeleteRuleButtonPressedEvent event) {
        ruleManager.deleteArchitectureRule(event.getRule());
        inputPresenter.updateArchitectureRulesLayout(ruleManager.getArchitectureRules());
    }

    private void handleEvent(final DeleteConceptRequestedEvent event) {
        conceptManager.removeConcept(event.getConcept());
        updateHierarchies(conceptManager, event.getSource());
    }

    private void handleEvent(final DeleteRelationRequestedEvent event) {
        relationManager.removeRelation(event.getRelation());
        updateHierarchies(relationManager, event.getSource());
    }

    private void updateHierarchies(HierarchyManager hierarchyManager, HierarchyView hv) {
        hv.setRoots(hierarchyManager.getRoots());
        hv.update();
    }

    private void addListeners() {
        view.addListener(
                ViewOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("ViewOptionRequested is not implemented"));
        view.addListener(
                HelpOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("HelpOptionRequested is not implemented"));
        view.addListener(ProjectOptionRequestedEvent.class, this::handleEvent);
        view.addListener(EditOptionRequestedEvent.class, EditOptionRequestedEvent::handleEvent);
        view.addListener(RulesOptionRequestedEvent.class, this::handleEvent);
        view.addListener(FooterOptionRequestedEvent.class, FooterOptionRequestedEvent::handleEvent);

        inputPresenter.addListener(OutputViewRequestedEvent.class, e -> selectPathForChecking());
        outputView.addListener(
                InputViewRequestedEvent.class, e -> view.showContent(inputPresenter.getView()));
    }

    public void selectPathForChecking() {
        SelectDirectoryDialog directoryDialog = new SelectDirectoryDialog();
        directoryDialog.addListener(
                RunToolchainRequestedEvent.class,
                event -> showOutputView(Optional.of(event.getSelectedPath())));
        directoryDialog.addListener(
                QuickOutputViewAccessRequestedEvent.class,
                event -> showOutputView(Optional.empty()));
        directoryDialog.open();
    }

    public void showOutputView(Optional<String> path) {
        try {
            ArchitectureCheck architectureCheck = new ArchitectureCheck();
            outputView.setResultRepository(architectureCheck.getRepository());
            if (path.isPresent()) {
                runArchCnlToolchain(architectureCheck, path.get());
            }
            outputView.displayResult(
                    architectureCheck
                            .getRepository()
                            .executeNativeSelectQuery(QueryUtils.getDefaultQuery()));
            view.showContent(outputView);
        } catch (PropertyNotFoundException e2) {
            view.showErrorMessage("Failed to connect to stardog database.");
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
        }
    }

    private void handleEvent(ProjectOptionRequestedEvent event) {
        switch (event.getOption()) {
            case NEW:
                view.showNewTab();
                break;
            case OPEN:
                OpenProjectDialog openProjectDialog =
                        new OpenProjectDialog(
                                projectManager, ruleManager, conceptManager, relationManager);
                openProjectDialog.addListener(
                        ShowFreeTextQueryRequestedEvent.class,
                        e -> outputView.showFreeTextQuery(e.getQuery(), e.isDefaultQueryTab()));
                openProjectDialog.addListener(
                        ShowCustomQueryRequestedEvent.class,
                        e -> outputView.showCustomQuery(e.getQuery(), e.isDefaultQueryTab()));
                List<ArchitectureRule> rules = ruleManager.getArchitectureRules();
                openProjectDialog.addListener(
                        ProjectOpenedEvent.class,
                        e -> inputPresenter.updateArchitectureRulesLayout(rules));
                openProjectDialog.open();
                break;
            case SAVE:
                try {
                    projectManager.saveProject(
                            ruleManager,
                            conceptManager,
                            relationManager,
                            outputView.getCustomQueries(),
                            outputView.getFreeTextQueries());
                } catch (final IOException e) {
                    new ConfirmDialog("Project file could not be written.").open();
                }
                break;
            case SAVE_AS:
                SaveProjectDialog dialog =
                        new SaveProjectDialog(
                                projectManager,
                                ruleManager,
                                conceptManager,
                                relationManager,
                                outputView.getCustomQueries(),
                                outputView.getFreeTextQueries());
                dialog.addListener(
                        ProjectSavedEvent.class, e -> view.setSaveProjectMenuItemEnabled(true));
                dialog.open();
                break;
            default:
                MainPresenter.LOG.warn(
                        "Unhandled ProjectOption {} appeared in ProjectOptionRequestedEvent.",
                        event.getOption());
                break;
        }
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

    public MainView getView() {
        return view;
    }
}
