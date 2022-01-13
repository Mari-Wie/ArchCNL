package org.archcnl.ui.outputview;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.ui.MainPresenter;
import org.archcnl.ui.outputview.components.AbstractQueryResultsComponent;
import org.archcnl.ui.outputview.components.CustomQueryUiComponent;
import org.archcnl.ui.outputview.components.FreeTextQueryUiComponent;
import org.archcnl.ui.outputview.components.QueryResultsUiComponent;
import org.archcnl.ui.outputview.components.SideBarLayout;
import org.archcnl.ui.outputview.events.CustomQueryInsertionRequestedEvent;

public class OutputView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    AbstractQueryResultsComponent queryResults;
    AbstractQueryResultsComponent customQueryResults;
    AbstractQueryResultsComponent freeTextQuery;
    SideBarLayout sideBar;
    Component currentComponent;

    public OutputView(final MainPresenter mainPresenter) throws PropertyNotFoundException {
        queryResults = new QueryResultsUiComponent();
        customQueryResults = new CustomQueryUiComponent();
        freeTextQuery = new FreeTextQueryUiComponent();
        sideBar = new SideBarLayout(this, mainPresenter);
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

    protected void registerEventListeners() {
        freeTextQuery.addListener(
                CustomQueryInsertionRequestedEvent.class,
                e -> this.insertCustomQueryIntoFreeTextQuery());
    }

    // TODO Extract into interface
    //
    public void switchToQueryView() {
        replace(currentComponent, queryResults);
        currentComponent = queryResults;
    }

    public void switchToCustomQueryView() {
        replace(currentComponent, customQueryResults);
        currentComponent = customQueryResults;
    }

    public void switchToFreeTextQueryView() {
        replace(currentComponent, freeTextQuery);
        currentComponent = freeTextQuery;
    }

    private void insertCustomQueryIntoFreeTextQuery() {
        final String customQuery = customQueryResults.getQuery();
        freeTextQuery.setQueryText(customQuery);
    }
}
