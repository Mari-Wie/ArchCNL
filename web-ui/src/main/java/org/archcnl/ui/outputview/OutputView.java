package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.toolchain.CNLToolchain;
import org.archcnl.ui.outputview.components.AbstractQueryResultsComponent;
import org.archcnl.ui.outputview.components.CustomQueryPresenter;
import org.archcnl.ui.outputview.components.CustomQueryView;
import org.archcnl.ui.outputview.components.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.components.GridView;
import org.archcnl.ui.outputview.components.QueryResultsUiComponent;
import org.archcnl.ui.outputview.components.SideBarLayout;
import org.archcnl.ui.outputview.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.events.PinQueryRequestedEvent;
import org.archcnl.ui.outputview.events.RunQueryRequestedEvent;

public class OutputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger(OutputView.class);

    private AbstractQueryResultsComponent queryResults;
    private CustomQueryPresenter customQueryPresenter;
    private AbstractQueryResultsComponent freeTextQuery;
    private SideBarLayout sideBar;
    private Component currentComponent;
    private ResultRepository resultRepository;

    public OutputView() throws PropertyNotFoundException {
    	OutputCtrl outputCtrl = new OutputCtrl();
    	outputCtrl.checkForViolations();
        resultRepository = outputCtrl.getRepository();
        // The execution of the default query should be moved into an OnAttachEvent
        queryResults = new QueryResultsUiComponent(prepareDefaultQueryGridView());
        customQueryPresenter = new CustomQueryPresenter();
        freeTextQuery = new FreeTextQueryUiComponent();
        sideBar = new SideBarLayout(this);

        initLayout();
        registerEventListeners();
        addAndExpand(sideBar, queryResults);
    }

    private void initLayout() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
        currentComponent = queryResults;
    }

    private void registerEventListeners() {
        freeTextQuery.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
        freeTextQuery.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        sideBar.addListener(InputViewRequestedEvent.class, this::fireEvent);
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
    }

    private void handleEvent(PinQueryRequestedEvent event) {
        sideBar.addPinnedCustomQueryButton(event.getSource());
        switchToCustomQueryView(event.getSource().getView());
        customQueryPresenter = new CustomQueryPresenter();
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
    }

    private void handleEvent(RunQueryRequestedEvent event) {
        Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getGridView().update(result);
    }

    private void handleEvent(FreeTextRunButtonPressedEvent event) {
        Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getSource().update(result);
    }

    public void switchToQueryView() {
        replace(currentComponent, queryResults);
        currentComponent = queryResults;
    }

    public void switchToCustomQueryView() {
        replace(currentComponent, customQueryPresenter.getView());
        currentComponent = customQueryPresenter.getView();
    }

    public void switchToCustomQueryView(CustomQueryView customQueryView) {
        replace(currentComponent, customQueryView);
        currentComponent = customQueryView;
    }

    public void switchToFreeTextQueryView() {
        replace(currentComponent, freeTextQuery);
        currentComponent = freeTextQuery;
    }

    private void insertCustomQueryIntoFreeTextQuery() {
        final String customQuery = customQueryPresenter.getQuery();
        freeTextQuery.setQueryText(customQuery);
    }

    private GridView prepareDefaultQueryGridView() {
        GridView gridView = new GridView();
        Optional<Result> result =
                resultRepository.executeNativeSelectQuery(QueryUtils.getDefaultQuery());
        gridView.update(result);
        return gridView;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
