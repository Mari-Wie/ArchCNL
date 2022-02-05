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
import org.archcnl.ui.events.ConceptGridUpdateRequestedEvent;
import org.archcnl.ui.events.ConceptHierarchySwapRequestedEvent;
import org.archcnl.ui.events.RelationGridUpdateRequestedEvent;
import org.archcnl.ui.events.RelationHierarchySwapRequestedEvent;
import org.archcnl.ui.outputview.queryviews.CustomQueryPresenter;
import org.archcnl.ui.outputview.queryviews.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.queryviews.QueryResultsUiComponent;
import org.archcnl.ui.outputview.queryviews.events.CustomQueryInsertionRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.FreeTextRunButtonPressedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinCustomQueryRequestedEvent;
import org.archcnl.ui.outputview.queryviews.events.PinFreeTextQueryRequestedEvent;
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

    public OutputView(String defaultQuery) {
        defaultQueryView = new QueryResultsUiComponent(defaultQuery);
        freeTextQueryView = createFreeTextQueryView(defaultQuery);
        sideBarWidget =
                new SideBarWidget(
                        defaultQueryView, customQueryPresenter.getView(), freeTextQueryView);

        addAndExpand(sideBarWidget, defaultQueryView);
    }
    
    private FreeTextQueryUiComponent createFreeTextQueryView(String query) {
        FreeTextQueryUiComponent newComponent = new FreeTextQueryUiComponent(query);
        newComponent.addListener(
                CustomQueryInsertionRequestedEvent.class, this::fireEvent);
        newComponent.addListener(FreeTextRunButtonPressedEvent.class, this::fireEvent);
        newComponent.addListener(PinFreeTextQueryRequestedEvent.class, this::fireEvent);
        return newComponent;
    }
    
    private void switchToComponent(final Component component) {
        replace(currentComponent, component);
        currentComponent = component;
    }
    
    public void displayResult(final Optional<Result> result) {
    	defaultQueryView.updateGridView(result);
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
