package org.archcnl.ui.events;

import com.vaadin.flow.component.ComponentEvent;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRuleManager;
import org.archcnl.ui.MainView;
import org.archcnl.ui.common.dialogs.OkDialog;
import org.archcnl.ui.inputview.InputPresenter;
import org.archcnl.ui.menudialog.OpenProjectDialog;
import org.archcnl.ui.menudialog.SaveProjectDialog;
import org.archcnl.ui.menudialog.events.ProjectOpenedEvent;
import org.archcnl.ui.menudialog.events.ProjectSavedEvent;
import org.archcnl.ui.menudialog.events.ShowCustomQueryRequestedEvent;
import org.archcnl.ui.menudialog.events.ShowFreeTextQueryRequestedEvent;
import org.archcnl.ui.outputview.OutputPresenter;

public class ProjectOptionRequestedEvent extends ComponentEvent<MainView> {

    public enum ProjectOption {
        NEW,
        OPEN,
        SAVE,
        SAVE_AS
    };

    private static final long serialVersionUID = 8536361781976666283L;
    private static final Logger LOG = LogManager.getLogger(ProjectOptionRequestedEvent.class);
    private ProjectOption option;

    public ProjectOptionRequestedEvent(
            final MainView source, final boolean fromClient, final ProjectOption option) {
        super(source, fromClient);
        this.option = option;
    }

    public ProjectOption getOption() {
        return option;
    }

    public void handleEvent(
            MainView mainView,
            ProjectManager projectManager,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            OutputPresenter outputPresenter,
            InputPresenter inputPresenter) {
        switch (option) {
            case NEW:
                mainView.showNewTab();
                break;
            case OPEN:
                performOpenAction(
                        projectManager,
                        ruleManager,
                        conceptManager,
                        relationManager,
                        outputPresenter,
                        inputPresenter);
                break;
            case SAVE:
                performSaveAction(
                        projectManager,
                        ruleManager,
                        conceptManager,
                        relationManager,
                        outputPresenter);
                break;
            case SAVE_AS:
                performSaveAsAction(
                        projectManager,
                        ruleManager,
                        conceptManager,
                        relationManager,
                        outputPresenter,
                        mainView);
                break;
            default:
                ProjectOptionRequestedEvent.LOG.warn(
                        "Unhandled ProjectOption {} appeared in ProjectOptionRequestedEvent.",
                        option);
                break;
        }
    }

    private void performOpenAction(
            ProjectManager projectManager,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            OutputPresenter outputPresenter,
            InputPresenter inputPresenter) {
        OpenProjectDialog openProjectDialog =
                new OpenProjectDialog(projectManager, ruleManager, conceptManager, relationManager);
        openProjectDialog.addListener(
                ShowFreeTextQueryRequestedEvent.class,
                e -> outputPresenter.showFreeTextQuery(e.getQuery(), e.isDefaultQueryTab()));
        openProjectDialog.addListener(
                ShowCustomQueryRequestedEvent.class,
                e -> outputPresenter.showCustomQuery(e.getQuery(), e.isDefaultQueryTab()));
        List<ArchitectureRule> rules = ruleManager.getArchitectureRules();
        openProjectDialog.addListener(
                ProjectOpenedEvent.class, e -> inputPresenter.updateArchitectureRulesLayout(rules));
        openProjectDialog.open();
    }

    private void performSaveAction(
            ProjectManager projectManager,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            OutputPresenter outputPresenter) {
        try {
            projectManager.saveProject(
                    ruleManager,
                    conceptManager,
                    relationManager,
                    outputPresenter.getCustomQueries(),
                    outputPresenter.getFreeTextQueries());
        } catch (final IOException e) {
            new OkDialog("Project file could not be written.").open();
        }
    }

    private void performSaveAsAction(
            ProjectManager projectManager,
            ArchitectureRuleManager ruleManager,
            ConceptManager conceptManager,
            RelationManager relationManager,
            OutputPresenter outputPresenter,
            MainView mainView) {
        SaveProjectDialog dialog =
                new SaveProjectDialog(
                        projectManager,
                        ruleManager,
                        conceptManager,
                        relationManager,
                        outputPresenter.getCustomQueries(),
                        outputPresenter.getFreeTextQueries());
        dialog.addListener(
                ProjectSavedEvent.class, e -> mainView.setSaveProjectMenuItemEnabled(true));
        dialog.open();
    }
}
