package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.ui.outputview.queryviews.CustomQueryView;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryComponent;
import org.archcnl.ui.outputview.queryviews.PredefinedQueryComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsComponent;
import org.archcnl.ui.outputview.sidebar.SideBarWidget;
import org.archcnl.ui.outputview.sidebar.events.InputViewRequestedEvent;
import org.archcnl.ui.outputview.sidebar.events.ShowComponentRequestedEvent;

public class OutputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    private QueryResultsComponent defaultQueryView;
    private List<PredefinedQueryComponent> predefinedQueries;
    private FreeTextQueryComponent freeTextQueryView;
    private CustomQueryView customQueryView;
    private SideBarWidget sideBarWidget;
    private Component currentComponent;

    public OutputView(
            CustomQueryView customQueryView,
            List<PredefinedQueryComponent> predefinedQueries,
            FreeTextQueryComponent freeTextQueryView,
            String defaultQuery) {
        defaultQueryView = new QueryResultsComponent(defaultQuery);
        this.predefinedQueries = predefinedQueries;
        this.customQueryView = customQueryView;
        this.freeTextQueryView = freeTextQueryView;
        initSideBarWidget();
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        currentComponent = defaultQueryView;
        addAndExpand(sideBarWidget, defaultQueryView);
    }

    private void initSideBarWidget() {
        sideBarWidget =
                new SideBarWidget(
                        defaultQueryView, predefinedQueries, customQueryView, freeTextQueryView);
        sideBarWidget.addListener(InputViewRequestedEvent.class, this::fireEvent);
        sideBarWidget.addListener(ShowComponentRequestedEvent.class, this::fireEvent);
        sideBarWidget.setWidth(15, Unit.PERCENTAGE);
        sideBarWidget.addClassName("side-bar");
    }

    public SideBarWidget getSideBarWidget() {
        return sideBarWidget;
    }

    public void updateCustomQueryView() {
        customQueryView.updateHierarchies();
    }

    public void switchToComponent(final Component component) {
        replace(currentComponent, component);
        currentComponent = component;
    }

    public void displayResult(
            final Optional<Result> defaultQueryResult,
            List<Optional<Result>> predefinedQueryResults,
            String nrOfViolations,
            String nrOfPackages,
            String nrOfRelationships,
            String nrOfTypes) {
        defaultQueryView.updateGridView(defaultQueryResult);
        for (int i = 0;
                i < Math.min(predefinedQueryResults.size(), predefinedQueries.size());
                i++) {
            predefinedQueries.get(i).updateGridView(predefinedQueryResults.get(i));
        }
        defaultQueryView.updateGeneralInfoLayout(
                nrOfViolations, nrOfPackages, nrOfRelationships, nrOfTypes);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
