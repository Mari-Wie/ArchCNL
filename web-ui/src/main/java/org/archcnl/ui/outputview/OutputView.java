package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.Optional;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
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

    private void initLayout() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBarWidget.setWidth(15, Unit.PERCENTAGE);
        defaultQueryView.setWidth(85, Unit.PERCENTAGE);
        currentComponent = defaultQueryView;
    }

    private void registerEventListeners() {
        freeTextQueryView.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
        freeTextQueryView.addListener(FreeTextRunButtonPressedEvent.class, this::handleEvent);
        sideBarWidget.addListener(InputViewRequestedEvent.class, this::fireEvent);
        sideBarWidget.addListener(
                ShowComponentRequestedEvent.class,
                event -> switchToComponent(event.getComponent()));
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
    }

    private void handleEvent(PinQueryRequestedEvent event) {
        sideBarWidget.addPinnedCustomQueryTab(
                event.getSource().getView(), event.getSource().getQueryName());
        customQueryPresenter = new CustomQueryPresenter();
        customQueryPresenter.addListener(PinQueryRequestedEvent.class, this::handleEvent);
        customQueryPresenter.addListener(RunQueryRequestedEvent.class, this::handleEvent);
        sideBarWidget.updateCustomQueryTab(customQueryPresenter.getView());
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
