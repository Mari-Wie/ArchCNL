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
import org.archcnl.domain.output.model.query.FreeTextQuery;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsUiComponent;
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

    public OutputView() {
        final String defaultQuery = QueryUtils.getDefaultQuery().transformToGui();
        defaultQueryView = new QueryResultsUiComponent(defaultQuery);
        customQueryPresenter = new CustomQueryPresenter();
        freeTextQueryView = new FreeTextQueryUiComponent(defaultQuery);
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

    public void showFreeTextQuery(FreeTextQuery query, boolean defaultQueryTab) {
        if (defaultQueryTab) {
            freeTextQueryView.setQueryText(query.getQueryString());
            freeTextQueryView.setQueryName(query.getName());
        } else {
            FreeTextQueryUiComponent newFreeTextQueryView = new FreeTextQueryUiComponent("");
            newFreeTextQueryView.setQueryText(query.getQueryString());
            newFreeTextQueryView.setQueryName(query.getName());
            pinnedFreeTextQueries.add(newFreeTextQueryView);
            addFreeTextQueryListener(newFreeTextQueryView);
            sideBarWidget.addPinnedQueryTab(newFreeTextQueryView, query.getName());
        }
    }

    public void showCustomQuery(Query query, boolean defaultQueryTab) {
        if (defaultQueryTab) {
            customQueryPresenter.setQueryName(query.getName());
            customQueryPresenter.setSelectClause(query.getSelectClause());
            customQueryPresenter.setWhereClause(query.getWhereClause());
        } else {
            CustomQueryPresenter newCustomQueryPresenter = new CustomQueryPresenter();
            newCustomQueryPresenter.setQueryName(query.getName());
            newCustomQueryPresenter.setSelectClause(query.getSelectClause());
            newCustomQueryPresenter.setWhereClause(query.getWhereClause());
            pinnedCustomQueries.add(newCustomQueryPresenter);
            addCustomQueryListener(newCustomQueryPresenter);
            sideBarWidget.addPinnedQueryTab(newCustomQueryPresenter.getView(), query.getName());
        }
    }

    private void initLayout() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBarWidget.setWidth(15, Unit.PERCENTAGE);
        sideBarWidget.addClassName("side-bar");
        currentComponent = defaultQueryView;
    }

    private void registerEventListeners() {
        addFreeTextQueryListener(freeTextQueryView);
        sideBarWidget.addListener(InputViewRequestedEvent.class, this::fireEvent);
        sideBarWidget.addListener(
                ShowComponentRequestedEvent.class,
                event -> switchToComponent(event.getComponent()));
        addCustomQueryListener(customQueryPresenter);
    }

    private void addFreeTextQueryListener(FreeTextQueryUiComponent freeTextQueryView) {
        freeTextQueryView.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
        freeTextQueryView.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        freeTextQueryView.addListener(PinQueryRequestedEvent.class, this::handleEvent);
    }

    private void addCustomQueryListener(CustomQueryPresenter customQueryPresenter) {
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
    }

    private void handleEvent(PinQueryRequestedEvent event) {
        sideBarWidget.addPinnedQueryTab(event.getLinkedComponent(), event.getQueryName());
        if (event.getSource() instanceof CustomQueryPresenter) {
            pinnedCustomQueries.add(customQueryPresenter);
            customQueryPresenter = new CustomQueryPresenter();
            addCustomQueryListener(customQueryPresenter);
            sideBarWidget.updateCustomQueryTab(customQueryPresenter.getView());
        } else {
            pinnedFreeTextQueries.add(freeTextQueryView);
            freeTextQueryView =
                    new FreeTextQueryUiComponent(QueryUtils.getDefaultQuery().transformToGui());
            addFreeTextQueryListener(freeTextQueryView);
            sideBarWidget.updateFreeTextQueryTab(freeTextQueryView);
        }
    }

    private void handleEvent(final RunQueryRequestedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getGridView().update(result);
    }

    private void handleEvent(final FreeTextRunButtonPressedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getSource().update(result);
    }

    private void switchToComponent(final Component component) {
        replace(currentComponent, component);
        currentComponent = component;
    }

    private void insertCustomQueryIntoFreeTextQuery() {
        final String customQuery = customQueryPresenter.getQuery();
        freeTextQueryView.setQueryText(customQuery);
    }

    public void displayResult(final Optional<Result> result) {
        defaultQueryView.updateGridView(result);
    }

    public void setResultRepository(final ResultRepository repository) {
        resultRepository = repository;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
