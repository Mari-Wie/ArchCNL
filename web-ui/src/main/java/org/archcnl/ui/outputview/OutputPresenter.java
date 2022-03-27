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
import org.archcnl.domain.output.model.query.PredefinedQuery;
import org.archcnl.domain.output.model.query.Query;
import org.archcnl.domain.output.model.query.QueryUtils;
import org.archcnl.domain.output.repository.ResultRepository;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.PredicateSelectedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.NodeAddRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.common.conceptandrelationlistview.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryComponent;
import org.archcnl.ui.outputview.queryviews.PredefinedQueryComponent;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.DeleteButtonPressedEvent;
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
    private FreeTextQueryComponent freeTextQueryView;
    private ResultRepository resultRepository;
    private List<CustomQueryPresenter> pinnedCustomQueries = new LinkedList<>();
    private List<FreeTextQueryComponent> pinnedFreeTextQueries = new LinkedList<>();

    public OutputPresenter() {
        final String defaultQuery = QueryUtils.getDefaultQuery();
        customQueryPresenter = createCustomQueryPresenter();
        freeTextQueryView = createFreeTextQueryView(defaultQuery);
        List<PredefinedQueryComponent> predefinedQueryComponents =
                createPredefinedQueryComponents();
        view =
                new OutputView(
                        customQueryPresenter.getView(),
                        predefinedQueryComponents,
                        freeTextQueryView,
                        defaultQuery);
        addListeners();
    }

    private void addListeners() {
        view.addListener(CustomQueryInsertionRequestedEvent.class, this::handleEvent);
        view.addListener(RunQueryRequestedEvent.class, this::handleEvent);
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
        for (FreeTextQueryComponent queryView : pinnedFreeTextQueries) {
            queries.add(queryView.makeQuery());
        }
        return queries;
    }

    public void showFreeTextQuery(FreeTextQuery query, boolean defaultQueryTab) {
        if (defaultQueryTab) {
            freeTextQueryView.setQueryText(query.getQueryString());
            freeTextQueryView.setQueryName(query.getName());
        } else {
            FreeTextQueryComponent newFreeTextQueryView = createFreeTextQueryView("");
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

        newCustomQueryPresenter.addListener(NodeAddRequestedEvent.class, this::fireEvent);
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

    private FreeTextQueryComponent createFreeTextQueryView(String query) {
        FreeTextQueryComponent newComponent = new FreeTextQueryComponent(query);
        newComponent.addListener(CustomQueryInsertionRequestedEvent.class, this::handleEvent);
        newComponent.addListener(RunQueryRequestedEvent.class, this::handleEvent);
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

    private List<PredefinedQueryComponent> createPredefinedQueryComponents() {
        List<PredefinedQueryComponent> predefinedQueryComponents =
                new LinkedList<PredefinedQueryComponent>();
        for (PredefinedQuery query : QueryUtils.getPredefinedQueries()) {
            PredefinedQueryComponent queryComponent = new PredefinedQueryComponent(query);
            predefinedQueryComponents.add(queryComponent);
            queryComponent.addListener(RunQueryRequestedEvent.class, this::handleEvent);
        }
        return predefinedQueryComponents;
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

    private void handleEvent(final QueryNameUpdateRequestedEvent event) {
        view.getSideBarWidget().updatePinnedQueryName(event);
    }

    public void displayResult(final Optional<Result> result) {
        String nrOfViolations = "-1";
        String nrOfPackages = "-1";
        String nrOfRelationships = "-1";
        String nrOfTypes = "-1";
        Optional<StardogDatabaseAPI.Result> nrOfViolationsResult =
                resultRepository.executeNativeSelectQuery(
                        QueryUtils.getQueryFromQueryDirectory(QueryUtils.NUMBER_OF_VIOLATIONS));
        Optional<StardogDatabaseAPI.Result> nrOfPackagesResult =
                resultRepository.executeNativeSelectQuery(
                        QueryUtils.getQueryFromQueryDirectory(QueryUtils.NUMBER_OF_PACKAGES));
        Optional<StardogDatabaseAPI.Result> nrOfRelationshipsResult =
                resultRepository.executeNativeSelectQuery(
                        QueryUtils.getQueryFromQueryDirectory(QueryUtils.NUMBER_OF_RELATIONSHIPS));
        Optional<StardogDatabaseAPI.Result> nrOfTypesResult =
                resultRepository.executeNativeSelectQuery(
                        QueryUtils.getQueryFromQueryDirectory(QueryUtils.NUMBER_OF_TYPES));

        if (nrOfViolationsResult.isPresent()) {
            nrOfViolations = nrOfViolationsResult.get().getViolations().get(0).get(0);
        }
        if (nrOfPackagesResult.isPresent()) {
            nrOfPackages = nrOfPackagesResult.get().getViolations().get(0).get(0);
        }
        if (nrOfRelationshipsResult.isPresent()) {
            nrOfRelationships = nrOfRelationshipsResult.get().getViolations().get(0).get(0);
        }
        if (nrOfTypesResult.isPresent()) {
            nrOfTypes = nrOfTypesResult.get().getViolations().get(0).get(0);
        }

        view.displayResult(result, nrOfViolations, nrOfPackages, nrOfRelationships, nrOfTypes);
    }

    public void setResultRepository(final ResultRepository repository) {
        resultRepository = repository;
    }

    public void updateCustomQueryView() {
        view.updateCustomQueryView();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
