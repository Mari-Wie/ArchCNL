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
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchyMoveRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchyMoveRequestedEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsUiComponent;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.DeleteButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinCustomQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinFreeTextQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.QueryNameUpdateRequestedEvent;
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
        final String defaultQuery = QueryUtils.getDefaultQuery();
        defaultQueryView = new QueryResultsUiComponent(defaultQuery);
        customQueryPresenter = createCustomQueryPresenter();
        freeTextQueryView = createFreeTextQueryView(defaultQuery);
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
            FreeTextQueryUiComponent newFreeTextQueryView = createFreeTextQueryView("");
            newFreeTextQueryView.setQueryText(query.getQueryString());
            newFreeTextQueryView.setQueryName(query.getName());
            pinnedFreeTextQueries.add(newFreeTextQueryView);
            sideBarWidget.addPinnedQueryTab(newFreeTextQueryView, query.getName());
        }
    }

    public void showCustomQuery(Query query, boolean defaultQueryTab) {
        if (defaultQueryTab) {
            customQueryPresenter.setQueryName(query.getName());
            customQueryPresenter.setSelectClause(query.getSelectClause());
            customQueryPresenter.setWhereClause(query.getWhereClause());
        } else {
            CustomQueryPresenter newCustomQueryPresenter = createCustomQueryPresenter();
            newCustomQueryPresenter.setQueryName(query.getName());
            newCustomQueryPresenter.setSelectClause(query.getSelectClause());
            newCustomQueryPresenter.setWhereClause(query.getWhereClause());
            pinnedCustomQueries.add(newCustomQueryPresenter);
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

    private CustomQueryPresenter createCustomQueryPresenter() {
        CustomQueryPresenter newCustomQueryPresenter = new CustomQueryPresenter();
        newCustomQueryPresenter.addListener(PinCustomQueryRequestedEvent.class, this::handleEvent);
        newCustomQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
        newCustomQueryPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                RelationGridUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                ConceptHierarchyMoveRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                RelationHierarchyMoveRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(PredicateSelectedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                RelationListUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(ConceptSelectedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(QueryNameUpdateRequestedEvent.class, this::handleEvent);
        newCustomQueryPresenter.addListener(
                DeleteButtonPressedEvent.class,
                event -> {
                    pinnedCustomQueries.remove(newCustomQueryPresenter);
                    sideBarWidget.deletePinnedQuery(event);
                });
        return newCustomQueryPresenter;
    }

    private void registerEventListeners() {
        sideBarWidget.addListener(InputViewRequestedEvent.class, this::fireEvent);
        sideBarWidget.addListener(
                ShowComponentRequestedEvent.class,
                event -> switchToComponent(event.getComponent()));
    }

    private FreeTextQueryUiComponent createFreeTextQueryView(String query) {
        FreeTextQueryUiComponent newComponent = new FreeTextQueryUiComponent(query);
        newComponent.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
        newComponent.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        newComponent.addListener(PinFreeTextQueryRequestedEvent.class, this::handleEvent);
        newComponent.addListener(QueryNameUpdateRequestedEvent.class, this::handleEvent);
        newComponent.addListener(
                DeleteButtonPressedEvent.class,
                event -> {
                    pinnedFreeTextQueries.remove(newComponent);
                    sideBarWidget.deletePinnedQuery(event);
                });
        return newComponent;
    }

    private void handleEvent(PinCustomQueryRequestedEvent event) {
        sideBarWidget.addPinnedQueryTab(event.getLinkedView(), event.getQueryName());
        pinnedCustomQueries.add(customQueryPresenter);
        customQueryPresenter = createCustomQueryPresenter();
        sideBarWidget.updateCustomQueryTab(customQueryPresenter.getView());
    }

    private void handleEvent(PinFreeTextQueryRequestedEvent event) {
        sideBarWidget.addPinnedQueryTab(event.getSource(), event.getQueryName());
        pinnedFreeTextQueries.add(freeTextQueryView);
        freeTextQueryView = createFreeTextQueryView(QueryUtils.getDefaultQuery());
        sideBarWidget.updateFreeTextQueryTab(freeTextQueryView);
    }

    private void handleEvent(final RunQueryRequestedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getGridView().update(result);
    }

    private void handleEvent(final FreeTextRunButtonPressedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getSource().update(result);
    }

    private void handleEvent(final QueryNameUpdateRequestedEvent event) {
        sideBarWidget.updatePinnedQueryName(event);
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
