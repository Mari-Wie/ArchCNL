package org.archcnl.ui.output.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.stardogwrapper.impl.StardogDatabase;
import org.archcnl.ui.output.Violation;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);
    private Button clearButton = new Button("Clear", e -> this.clearGrid());

    private String username = "admin";
    private String password = "admin";
    private String databaseName = "archcnl_it_db";
    private String server = "http://localhost:5820";
    private StardogDatabaseAPI db;

    public GridView() {
        this.db = new StardogDatabase(server, databaseName, username, password);
        grid.setHeightByRows(true);
        add(grid, clearButton);
    }

    public void update(final String queryString) {
        Optional<Result> res = Optional.empty();
        try {
            db.connect(false);
            res = db.executeSelectQuery(queryString);
            db.closeConnectionToServer();
        } catch (final Exception ex) {
            // TODO add logger for exception case
            ex.printStackTrace();
        }
        if (res.isPresent()) {
            final StardogDatabaseAPI.Result results = res.get();
            try {
                if (results.getNumberOfViolations() > 0) {
                    for (final String varName : results.getVars()) {
                        grid.addColumn(item -> item.getVal(varName)).setHeader(varName);
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final ArrayList<ArrayList<String>> violations = results.getViolations();
            for (final ArrayList<String> violation : violations) {
                violationList.add(new Violation(results.getVars(), violation));
            }
        } else {
            // TODO Decide what to do with errors or wrong queries
        }

        grid.setItems(violationList);
    }

    public void clearGrid() {
        violationList = new ArrayList<>();
        grid.removeAllColumns();
    }
}
