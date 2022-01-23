package org.archcnl.ui.outputview.queryviews.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.Optional;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI.Result;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);

    public GridView() {
        grid.setHeightByRows(true);
        add(grid);
    }

    public void update(final Optional<Result> result) {
        clearGrid();
        if (result.isPresent()) {
            final StardogDatabaseAPI.Result results = result.get();
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

    private void clearGrid() {
        violationList = new ArrayList<>();
        grid.removeAllColumns();
    }
}
