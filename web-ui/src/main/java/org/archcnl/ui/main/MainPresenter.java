package org.archcnl.ui.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.ProjectManager;
import org.archcnl.ui.input.InputPresenter;
import org.archcnl.ui.input.InputView;
import org.archcnl.ui.main.events.EditOptionRequestedEvent;
import org.archcnl.ui.main.events.FooterOptionRequestedEvent;
import org.archcnl.ui.main.events.HelpOptionRequestedEvent;
import org.archcnl.ui.main.events.ProjectOptionRequestedEvent;
import org.archcnl.ui.main.events.RulesOptionRequestedEvent;
import org.archcnl.ui.main.events.ViewOptionRequestedEvent;
import org.archcnl.ui.output.component.QueryView;

public class MainPresenter implements PropertyChangeListener {

    private static final Logger LOG = LogManager.getLogger(MainPresenter.class);
    private final MainView view;
    private final QueryView outputView;
    private final InputView inputView;

    public MainPresenter() throws PropertyNotFoundException {
        final InputPresenter inputPresenter = new InputPresenter(this);
        inputView = new InputView(inputPresenter);
        outputView = new QueryView(this);
        view = new MainView(inputView);
        addListeners();
    }

    private void addListeners() {
        ProjectManager.getInstance().addPropertyChangeListener(this);
        view.addListener(
                ViewOptionRequestedEvent.class,
                e -> LOG.warn("ViewOptionRequested is not implemented"));
        view.addListener(
                HelpOptionRequestedEvent.class,
                e -> LOG.warn("HelpOptionRequested is not implemented"));
        view.addListener(
                ProjectOptionRequestedEvent.class, ProjectOptionRequestedEvent::handleEvent);
        view.addListener(EditOptionRequestedEvent.class, EditOptionRequestedEvent::handleEvent);
        view.addListener(RulesOptionRequestedEvent.class, RulesOptionRequestedEvent::handleEvent);
        view.addListener(FooterOptionRequestedEvent.class, FooterOptionRequestedEvent::handleEvent);
    }

    public MainView getView() {
        return view;
    }

    public void showArchitectureRuleView() {
        view.showContent(inputView);
    }

    public void showResultView() {
        view.showContent(outputView);
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        view.setSaveProjectMenuItemEnabled(true);
    }
}
