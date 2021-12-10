package org.archcnl.ui.output.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.archcnl.ui.main.MainPresenter;
import org.archcnl.ui.output.events.CustomQueryInsertionRequestedEvent;

public class QueryView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    AbstractQueryResultsComponent queryResults;
    AbstractQueryResultsComponent customQueryResults;
    AbstractQueryResultsComponent freeTextQuery;
    SideBarLayout sideBar;
    Component currentComponent;

    public QueryView(MainPresenter mainPresenter) {
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
        freeTextQuery.addListener(CustomQueryInsertionRequestedEvent.class, e -> this.insertCustomQueryIntoFreeTextQuery());
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
    	System.out.print("insertInView");
    	String customQuery = customQueryResults.getQuery();
    	freeTextQuery.setQueryText(customQuery);
    }
}
