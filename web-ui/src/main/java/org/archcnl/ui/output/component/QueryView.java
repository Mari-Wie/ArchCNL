package org.archcnl.ui.output.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 *
 * <p>To implement a Vaadin view just extend any Vaadin component and use @Route annotation to
 * announce it in a URL as a Spring managed bean. Use the @PWA annotation make the application
 * installable on phones, tablets and some desktop browsers.
 *
 * <p>A new instance of this class is created for every new user and every browser tab/window.
 */
@Route("QueryView")
public class QueryView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    AbstractQueryResults queryResults;
    AbstractQueryResults customQueryResults;
    SideBarLayout sideBar;

    public QueryView() {
        queryResults = new QueryResultsUiComponent();
        customQueryResults = new CustomQueryUiComponent();
        sideBar = new SideBarLayout(this);
        initLayout();
        addAndExpand(sideBar, queryResults);
    }

    private void initLayout() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
    }

    // TODO Extract into interface
    //
    public void switchToQueryView() {
        replace(customQueryResults, queryResults);
    }

    public void switchToCustomQueryView() {
        replace(queryResults, customQueryResults);
    }
}
