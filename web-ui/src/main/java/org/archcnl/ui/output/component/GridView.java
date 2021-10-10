package org.archcnl.ui.output.component;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;
import org.archcnl.ui.output.MockedRow;
import org.archcnl.ui.output.Violation;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);

    private String username = "admin";
    private String password = "admin";
    private String databaseName = "archcnl_it_db";
    private String server = "http://localhost:5820";
    private StardogDatabaseAPI db;

    public GridView() {
        this.db = new StardogDatabase(server, databaseName, username, password);
        grid.setHeightByRows(true);
    }

    public void update(final String queryString) {
        List<MockedRow> content = new ArrayList<>();
        content.add(
                new MockedRow(
                        1,
                        "Every Aggregate must resideIn a DomainRing.",
                        "ValidationAggregate",
                        "package api;",
                        "1",
                        "api.ValidationAggregate",
                        "Thursday, 14-Oct-21 14:56:32 UTC"));
        content.add(
                new MockedRow(
                        2,
                        "No Aggregate can use an ApplicationService.",
                        "UserAggregate",
                        "import api.UserService;",
                        "3",
                        "domain.UserAggregate",
                        "Thursday, 14-Oct-21 14:56:32 UTC"));
        content.add(
                new MockedRow(
                        3,
                        "No Aggregate can use an ApplicationService.",
                        "UserAggregate",
                        "private UserService userService;",
                        "8",
                        "domain.UserAggregate",
                        "Thursday, 14-Oct-21 14:56:32 UTC"));
        Grid<MockedRow> mockedGrid = new Grid<>(MockedRow.class);
        mockedGrid.setHeightByRows(true);
        mockedGrid.setItems(content);
        mockedGrid.setColumns(
                "index", "violates", "className", "line", "lineNumber", "fullName", "timeStamp");
        add(mockedGrid);
    }
}
