package org.archcnl.ui.outputview.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;
import org.archcnl.stardogwrapper.impl.StardogDatabase;
import org.archcnl.ui.outputview.Violation;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(GridView.class);

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);
    private Button clearButton = new Button("Clear", e -> this.clearGrid());

    private StardogDatabaseAPI db;

    public GridView() throws PropertyNotFoundException {
        this.db =
                new StardogDatabase(
                        ConfigAppService.getDbUrl(),
                        ConfigAppService.getDbName(),
                        ConfigAppService.getDbUsername(),
                        ConfigAppService.getDbPassword());
        GridView.LOG.info(
                "Connection to DB host '{}' will be created.", ConfigAppService.getDbUrl());
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
