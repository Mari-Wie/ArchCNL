package org.archcnl.ui.queryview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);

    private String username = "admin";
    private String password = "admin";
    private String databaseName = "archcnl_it_db";
    private String server = "http://localhost:5820";
    private StardogICVAPI icvAPI;
    private StardogDatabaseAPI db;

    public GridView() {

        this.db = new StardogDatabase(server, databaseName, username, password);
        this.icvAPI = StardogAPIFactory.getICVAPI(db);
        grid.setHeightByRows(true);
        add(grid);
    }

    public void update(String queryString) {
        Optional<StardogDatabaseAPI.Result> res = Optional.empty();
        try {
            db.connect(false);
            res = db.executeSelectQuery(queryString);
            db.closeConnectionToServer();
        } catch (Exception ex) {
            // TODO add logger for exception case
            ex.printStackTrace();
        }
        if (res.isPresent()) {
            StardogDatabaseAPI.Result results = res.get();
            try {
                if (results.getNumberOfViolations() > 0) {
                    for (String varName : results.getVars()) {
                        grid.addColumn(item -> item.getVal(varName)).setHeader(varName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<ArrayList<String>> violations = results.getViolations();
            for (ArrayList<String> violation : violations) {
                violationList.add(new Violation(results.getVars(), violation));
            }
        } else {
            // TODO Decide what to do with errors or wrong queries
        }

        grid.setItems(violationList);
    }
}
