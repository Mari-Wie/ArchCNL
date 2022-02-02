package org.archcnl.ui;

import com.complexible.stardog.StardogException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.common.ArchitectureCheck;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.HierarchyManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.ProjectManager;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.ui.common.conceptandrelationlistview.HierarchyView;
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
import org.archcnl.ui.menudialog.SelectDirectoryDialog;
import org.archcnl.ui.outputview.OutputView;
import org.archcnl.ui.outputview.events.InputViewRequestedEvent;

@Tag("MainPresenter")
public class MainPresenter extends Component implements PropertyChangeListener {

    private static final long serialVersionUID = -8850076288722393209L;
    private static final Logger LOG = LogManager.getLogger(MainPresenter.class);
    private final MainView view;
    private final OutputView outputView;
    private final InputPresenter inputPresenter;
    private ArchitectureCheck architectureCheck;

    public MainPresenter() {
        inputPresenter = new InputPresenter();
        inputPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        inputPresenter.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);

        outputView = new OutputView();
        outputView.addListener(ConceptGridUpdateRequestedEvent.class, this::handleEvent);
        outputView.addListener(RelationGridUpdateRequestedEvent.class, this::handleEvent);
        outputView.addListener(ConceptHierarchySwapRequestedEvent.class, this::handleEvent);
        outputView.addListener(RelationHierarchySwapRequestedEvent.class, this::handleEvent);

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
        ProjectManager.getInstance().addPropertyChangeListener(this);
        view.addListener(
                ViewOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("ViewOptionRequested is not implemented"));
        view.addListener(
                HelpOptionRequestedEvent.class,
                e -> MainPresenter.LOG.warn("HelpOptionRequested is not implemented"));
        view.addListener(
                ProjectOptionRequestedEvent.class, ProjectOptionRequestedEvent::handleEvent);
        view.addListener(EditOptionRequestedEvent.class, EditOptionRequestedEvent::handleEvent);
        view.addListener(RulesOptionRequestedEvent.class, RulesOptionRequestedEvent::handleEvent);
        view.addListener(FooterOptionRequestedEvent.class, FooterOptionRequestedEvent::handleEvent);

        inputPresenter.addListener(OutputViewRequestedEvent.class, e -> selectPathForChecking());
        outputView.addListener(
                InputViewRequestedEvent.class, e -> view.showContent(inputPresenter.getView()));
    }

    public void selectPathForChecking() {
        SelectDirectoryDialog directoryDialog = new SelectDirectoryDialog(architectureCheck);
        directoryDialog.addOpenedChangeListener(
                e -> {
                    if (!e.isOpened() && directoryDialog.isOkButtonPressed()) {
                        checkViolations(directoryDialog.getSelectedPath());
                    }
                });
        directoryDialog.open();
    }

    public void checkViolations(String path) {
        ResultRepository repository = null;
        try {
            architectureCheck = new ArchitectureCheck(path);
            repository = architectureCheck.getRepository();
        } catch (PropertyNotFoundException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Properties of database could not be read.");
        } catch (StardogException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Could not connect to the database.");
        } catch (IOException e) {
            view.showErrorMessage(
                    "An error occured while running the architecture check. Rules could not be generated.");
        } finally {
            // TODO Use a different way to ensure the initialization of the repository in outputView
            if (repository == null) {
                try {
                    repository =
                            new ResultRepositoryImpl(
                                    ConfigAppService.getDbUrl(),
                                    ConfigAppService.getDbName(),
                                    ConfigAppService.getDbUsername(),
                                    ConfigAppService.getDbPassword());
                } catch (PropertyNotFoundException e1) {
                    view.showErrorMessage("Creation of default database access failed.");
                }
            }
            outputView.displayResult(
                    repository.executeNativeSelectQuery(QueryUtils.getDefaultQuery()));
            outputView.setResultRepository(repository);
            // TODO Show error instead of outputView and add alternative quick dev access
            view.showContent(outputView);
        }
    }

    public MainView getView() {
        return view;
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        view.setSaveProjectMenuItemEnabled(true);
    }
}
