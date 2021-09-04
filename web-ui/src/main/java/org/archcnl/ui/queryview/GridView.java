package org.archcnl.ui.queryview;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;

public class GridView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);

    public GridView() {
        grid.setHeightByRows(true);
        add(grid);
    }

    public void update(final StardogDatabaseAPI.Result results) {
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

        grid.setItems(violationList);
    }
}
