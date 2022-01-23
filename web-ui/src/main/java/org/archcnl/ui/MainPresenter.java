package org.archcnl.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.ui.common.dialogs.ConfirmDialog;
import org.archcnl.ui.events.EditOptionRequestedEvent;
import org.archcnl.ui.events.FooterOptionRequestedEvent;
import org.archcnl.ui.events.HelpOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.events.RulesOptionRequestedEvent;
import org.archcnl.ui.events.ViewOptionRequestedEvent;
import org.archcnl.ui.inputview.InputPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.menudialog.OpenProjectDialog;
import org.archcnl.ui.menudialog.SaveProjectDialog;
import org.archcnl.ui.menudialog.events.ProjectSavedEvent;
import org.archcnl.ui.outputview.OutputView;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;

@Tag("MainPresenter")
public class MainPresenter extends Component {

    private static final long serialVersionUID = -8850076288722393209L;
    private static final Logger LOG = LogManager.getLogger(MainPresenter.class);
    private final MainView view;
    private final OutputView outputView;
    private final InputPresenter inputPresenter;

    public MainPresenter() throws PropertyNotFoundException {
        inputPresenter = new InputPresenter();
        outputView = new OutputView();
        view = new MainView(inputPresenter.getView());
        addListeners();
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

        inputPresenter.addListener(
                OutputViewRequestedEvent.class, e -> view.showContent(outputView));
        outputView.addListener(
                InputViewRequestedEvent.class, e -> view.showContent(inputPresenter.getView()));
    }

    private void handleEvent(ProjectOptionRequestedEvent event) {
        switch (event.getOption()) {
            case NEW:
                view.showNewTab();
                break;
            case OPEN:
                new OpenProjectDialog().open();
                break;
            case SAVE:
                try {
                    ProjectManager.getInstance()
                            .saveProject(
                                    outputView.getCustomQueries(), outputView.getFreeTextQueries());
                } catch (final IOException e) {
                    new ConfirmDialog("Project file could not be written.").open();
                }
                break;
            case SAVE_AS:
                SaveProjectDialog dialog =
                        new SaveProjectDialog(
                                outputView.getCustomQueries(), outputView.getFreeTextQueries());
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
