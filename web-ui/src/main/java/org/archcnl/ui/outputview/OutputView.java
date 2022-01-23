package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.domain.output.repository.ResultRepositoryImpl;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsUiComponent;
import org.archcnl.ui.outputview.queryviews.components.GridView;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.RunQueryRequestedEvent;
import org.archcnl.ui.outputview.sidebar.SideBarWidget;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

public class OutputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private QueryResultsUiComponent defaultQueryView;
    private CustomQueryPresenter customQueryPresenter;
    private FreeTextQueryUiComponent freeTextQueryView;
    private SideBarWidget sideBarWidget;
    private Component currentComponent;
    private ResultRepository resultRepository;
    private List<CustomQueryPresenter> pinnedCustomQueries = new LinkedList<>();
    private List<FreeTextQueryUiComponent> pinnedFreeTextQueries = new LinkedList<>();

    public OutputView() throws PropertyNotFoundException {
        resultRepository =
                new ResultRepositoryImpl(
                        ConfigAppService.getDbUrl(),
                        ConfigAppService.getDbName(),
                        ConfigAppService.getDbUsername(),
                        ConfigAppService.getDbPassword());
        // The execution of the default query should be moved into an OnAttachEvent
        defaultQueryView = new QueryResultsUiComponent(prepareDefaultQueryGridView());
        customQueryPresenter = new CustomQueryPresenter();
        freeTextQueryView = new FreeTextQueryUiComponent();
        sideBarWidget =
                new SideBarWidget(
                        defaultQueryView, customQueryPresenter.getView(), freeTextQueryView);

        initLayout();
        registerEventListeners();
        addAndExpand(sideBarWidget, defaultQueryView);
    }

    public List<Query> getCustomQueries() {
        List<Query> queries = new LinkedList<>();
        queries.add(customQueryPresenter.makeQuery());
        for (CustomQueryPresenter presenter : pinnedCustomQueries) {
            queries.add(presenter.makeQuery());
        }
        return queries;
    }

    public List<FreeTextQuery> getFreeTextQueries() {
        List<FreeTextQuery> queries = new LinkedList<>();
        queries.add(freeTextQueryView.makeQuery());
        for (FreeTextQueryUiComponent queryView : pinnedFreeTextQueries) {
            queries.add(queryView.makeQuery());
        }
        return queries;
    }

    private void initLayout() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBarWidget.setWidth(15, Unit.PERCENTAGE);
        currentComponent = defaultQueryView;
    }

    private void registerEventListeners() {
        addFreeTextQueryListener();
        sideBarWidget.addListener(InputViewRequestedEvent.class, this::fireEvent);
        sideBarWidget.addListener(
                ShowComponentRequestedEvent.class,
                event -> switchToComponent(event.getComponent()));
        addCustomQueryListener();
    }

    private void addFreeTextQueryListener() {
        freeTextQueryView.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
        freeTextQueryView.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        freeTextQueryView.addListener(PinQueryRequestedEvent.class, this::handleEvent);
    }

    private void addCustomQueryListener() {
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
    }

    private void handleEvent(PinQueryRequestedEvent event) {
        sideBarWidget.addPinnedQueryTab(event.getLinkedComponent(), event.getQueryName());
        if (event.getSource() instanceof CustomQueryPresenter) {
            pinnedCustomQueries.add(customQueryPresenter);
            customQueryPresenter = new CustomQueryPresenter();
            addCustomQueryListener();
            sideBarWidget.updateCustomQueryTab(customQueryPresenter.getView());
        } else {
            pinnedFreeTextQueries.add(freeTextQueryView);
            freeTextQueryView = new FreeTextQueryUiComponent();
            addFreeTextQueryListener();
            sideBarWidget.updateFreeTextQueryTab(freeTextQueryView);
        }
    }

    private void handleEvent(RunQueryRequestedEvent event) {
        Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getGridView().update(result);
    }

    private void handleEvent(FreeTextRunButtonPressedEvent event) {
        Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getSource().update(result);
    }

    private void switchToComponent(Component component) {
        replace(currentComponent, component);
        currentComponent = component;
    }

    private void insertCustomQueryIntoFreeTextQuery() {
        final String customQuery = customQueryPresenter.getQuery();
        freeTextQueryView.setQueryText(customQuery);
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
