package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
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
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.DeleteButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinCustomQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinFreeTextQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.QueryNameUpdateRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.RunQueryRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

@Tag("OutputPresenter")
public class OutputPresenter extends Component {

    private static final long serialVersionUID = 1L;

    private OutputView view;
    private CustomQueryPresenter customQueryPresenter;
    private FreeTextQueryUiComponent freeTextQueryView;
    private ResultRepository resultRepository;
    private List<CustomQueryPresenter> pinnedCustomQueries = new LinkedList<>();
    private List<FreeTextQueryUiComponent> pinnedFreeTextQueries = new LinkedList<>();

    public OutputPresenter() {
        final String defaultQuery = QueryUtils.getDefaultQuery();
        customQueryPresenter = createCustomQueryPresenter();
        freeTextQueryView = createFreeTextQueryView(defaultQuery);
        view = new OutputView(customQueryPresenter.getView(), freeTextQueryView, defaultQuery);
        addListeners();
    }

    private void addListeners() {
        view.addListener(CustomQueryInsertionRequestedEvent.class, this::handleEvent);
        view.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        view.addListener(PinFreeTextQueryRequestedEvent.class, this::handleEvent);
        view.addListener(InputViewRequestedEvent.class, this::fireEvent);
        view.addListener(
                ShowComponentRequestedEvent.class,
                event -> view.switchToComponent(event.getComponent()));
    }

    public OutputView getView() {
        return view;
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
            view.getSideBarWidget().addPinnedQueryTab(newFreeTextQueryView, query.getName());
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
            view.getSideBarWidget()
                    .addPinnedQueryTab(newCustomQueryPresenter.getView(), query.getName());
        }
    }

    private CustomQueryPresenter createCustomQueryPresenter() {
        CustomQueryPresenter newCustomQueryPresenter = new CustomQueryPresenter();
        newCustomQueryPresenter.addListener(PinCustomQueryRequestedEvent.class, this::handleEvent);
        newCustomQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
        newCustomQueryPresenter.addListener(ConceptGridUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                RelationGridUpdateRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                ConceptHierarchySwapRequestedEvent.class, this::fireEvent);
        newCustomQueryPresenter.addListener(
                RelationHierarchySwapRequestedEvent.class, this::fireEvent);
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
                    view.getSideBarWidget().deletePinnedQuery(event);
                });
        return newCustomQueryPresenter;
    }

    private FreeTextQueryUiComponent createFreeTextQueryView(String query) {
        FreeTextQueryUiComponent newComponent = new FreeTextQueryUiComponent(query);
        newComponent.addListener(CustomQueryInsertionRequestedEvent.class, this::handleEvent);
        newComponent.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        newComponent.addListener(PinFreeTextQueryRequestedEvent.class, this::handleEvent);
        newComponent.addListener(QueryNameUpdateRequestedEvent.class, this::handleEvent);
        newComponent.addListener(
                DeleteButtonPressedEvent.class,
                event -> {
                    pinnedFreeTextQueries.remove(newComponent);
                    view.getSideBarWidget().deletePinnedQuery(event);
                });
        return newComponent;
    }

    private void handleEvent(PinCustomQueryRequestedEvent event) {
        view.getSideBarWidget().addPinnedQueryTab(event.getLinkedView(), event.getQueryName());
        pinnedCustomQueries.add(customQueryPresenter);
        customQueryPresenter = createCustomQueryPresenter();
        view.getSideBarWidget().updateCustomQueryTab(customQueryPresenter.getView());
    }

    private void handleEvent(PinFreeTextQueryRequestedEvent event) {
        view.getSideBarWidget().addPinnedQueryTab(event.getSource(), event.getQueryName());
        pinnedFreeTextQueries.add(freeTextQueryView);
        freeTextQueryView = createFreeTextQueryView(QueryUtils.getDefaultQuery());
        view.getSideBarWidget().updateFreeTextQueryTab(freeTextQueryView);
    }

    private void handleEvent(final RunQueryRequestedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getGridView().update(result);
    }

    private void handleEvent(final CustomQueryInsertionRequestedEvent event) {
        event.getSource().setQueryText(customQueryPresenter.getQuery());
    }

    private void handleEvent(final FreeTextRunButtonPressedEvent event) {
        final Optional<Result> result = resultRepository.executeNativeSelectQuery(event.getQuery());
        event.getSource().update(result);
    }

    private void handleEvent(final QueryNameUpdateRequestedEvent event) {
        view.getSideBarWidget().updatePinnedQueryName(event);
    }

    public void displayResult(final Optional<Result> result) {
        view.displayResult(result);
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
