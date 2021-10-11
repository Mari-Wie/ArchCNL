package org.archcnl.ui.output.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.archcnl.ui.main.MainPresenter;

public class QueryView extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    AbstractQueryResults queryResults;
    AbstractQueryResults customQueryResults;
    SideBarLayout sideBar;

    public QueryView(MainPresenter mainPresenter) {
        queryResults = new QueryResultsUiComponent();
        customQueryResults = new CustomQueryUiComponent();
        sideBar = new SideBarLayout(this, mainPresenter);
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
