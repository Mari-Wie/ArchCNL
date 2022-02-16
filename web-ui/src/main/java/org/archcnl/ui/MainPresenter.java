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
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;
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
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.menudialog.OpenProjectDialog;
import org.archcnl.ui.menudialog.SaveProjectDialog;
import org.archcnl.ui.menudialog.SelectDirectoryDialog;
import org.archcnl.ui.menudialog.events.ProjectSavedEvent;
import org.archcnl.ui.menudialog.events.QuickOutputViewAccessRequestedEvent;
import org.archcnl.ui.menudialog.events.RunToolchainRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowCustomQueryRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowFreeTextQueryRequestedEvent;
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

    public MainPresenter() {
        projectManager = new ProjectManager();
        inputPresenter = new InputPresenter();
        inputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);

        outputPresenter = new OutputPresenter();
        outputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        outputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);

        view = new MainView(inputPresenter.getView());
        addListeners();
    }

    public void handleEvent(final ConceptGridUpdateRequestedEvent event) {
        ConceptManager conceptManager = RulesConceptsAndRelations.getInstance().getConceptManager();
        updateHierarchies(conceptManager, event.getSource());
    }

    public void handleEvent(final RelationGridUpdateRequestedEvent event) {
        RelationManager relationManager =
                RulesConceptsAndRelations.getInstance().getRelationManager();
        updateHierarchies(relationManager, event.getSource());
    }

    public void handleEvent(final ConceptHierarchySwapRequestedEvent event) {
        ConceptManager conceptManager = RulesConceptsAndRelations.getInstance().getConceptManager();
        conceptManager.moveNode(event.getDraggedNode(), event.getTargetNode());
        updateHierarchies(conceptManager, event.getSource());
    }

    public void handleEvent(final RelationHierarchySwapRequestedEvent event) {

        RelationManager relationManager =
                RulesConceptsAndRelations.getInstance().getRelationManager();
        relationManager.moveNode(event.getDraggedNode(), event.getTargetNode());
        updateHierarchies(relationManager, event.getSource());
    }

    public void updateHierarchies(HierarchyManager hierarchyManager, HierarchyView hv) {
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
        view.addListener(RulesOptionRequestedEvent.class, RulesOptionRequestedEvent::handleEvent);
        view.addListener(FooterOptionRequestedEvent.class, FooterOptionRequestedEvent::handleEvent);

        inputPresenter.addListener(OutputViewRequestedEvent.class, e -> selectPathForChecking());
        outputPresenter.addListener(
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
            outputPresenter.setResultRepository(architectureCheck.getRepository());
            if (path.isPresent()) {
                runArchCnlToolchain(architectureCheck, path.get());
            }
            outputPresenter.displayResult(
                    architectureCheck
                            .getRepository()
                            .executeNativeSelectQuery(QueryUtils.getDefaultQuery()));
            view.showContent(outputPresenter.getView());
        } catch (PropertyNotFoundException e2) {
            view.showErrorMessage("Failed to connect to stardog database.");
        }
    }

    private void runArchCnlToolchain(ArchitectureCheck check, String path) {
        try {
            check.runToolchain(path);
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
                OpenProjectDialog openProjectDialog = new OpenProjectDialog(projectManager);
                openProjectDialog.addListener(
                        ShowFreeTextQueryRequestedEvent.class,
                        e ->
                                outputPresenter.showFreeTextQuery(
                                        e.getQuery(), e.isDefaultQueryTab()));
                openProjectDialog.addListener(
                        ShowCustomQueryRequestedEvent.class,
                        e -> outputPresenter.showCustomQuery(e.getQuery(), e.isDefaultQueryTab()));
                openProjectDialog.open();
                break;
            case SAVE:
                try {
                    projectManager.saveProject(
                            outputPresenter.getCustomQueries(),
                            outputPresenter.getFreeTextQueries());
                } catch (final IOException e) {
                    new ConfirmDialog("Project file could not be written.").open();
                }
                break;
            case SAVE_AS:
                SaveProjectDialog dialog =
                        new SaveProjectDialog(
                                projectManager,
                                outputPresenter.getCustomQueries(),
                                outputPresenter.getFreeTextQueries());
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

    public MainView getView() {
        return view;
    }
}
