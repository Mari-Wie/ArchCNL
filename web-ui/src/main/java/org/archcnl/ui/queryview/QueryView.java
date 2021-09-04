package org.archcnl.ui.queryview;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.example.common.SideBarLayout;

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

    AbstractQueryResults queryResults = new QueryResults();
    AbstractQueryResults customQueryResults = new CustomQueryResults();
    SideBarLayout sideBar = new SideBarLayout(this);

    // TODO Extract into interface
    //
    public void switchToQueryView() {
        replace(customQueryResults, queryResults);
    }

    public void switchToCustomQueryView() {
        replace(queryResults, customQueryResults);
    }

    public QueryView() {
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);
        sideBar.setWidth(20, Unit.PERCENTAGE);
        queryResults.setWidth(80, Unit.PERCENTAGE);
        addAndExpand(sideBar, queryResults);
    }
}
