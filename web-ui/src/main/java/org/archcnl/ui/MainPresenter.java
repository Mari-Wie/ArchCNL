package org.archcnl.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.common.ProjectManager;
import org.archcnl.ui.events.EditOptionRequestedEvent;
import org.archcnl.ui.events.FooterOptionRequestedEvent;
import org.archcnl.ui.events.HelpOptionRequestedEvent;
import org.archcnl.ui.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.events.RulesOptionRequestedEvent;
import org.archcnl.ui.events.ViewOptionRequestedEvent;
import org.archcnl.ui.inputview.InputPresenter;
import org.archcnl.ui.inputview.rulesormappingeditorview.events.OutputViewRequestedEvent;
import org.archcnl.ui.outputview.OutputView;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;

@Tag("MainPresenter")
public class MainPresenter extends Component implements PropertyChangeListener {

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

        inputPresenter.addListener(
                OutputViewRequestedEvent.class, e -> view.showContent(outputView));
        outputView.addListener(
                InputViewRequestedEvent.class, e -> view.showContent(inputPresenter.getView()));
    }

    public MainView getView() {
        return view;
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        view.setSaveProjectMenuItemEnabled(true);
    }
}
