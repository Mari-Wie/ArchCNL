package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import com.vaadin.flow.component.grid.Grid;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;

public class GridView extends VerticalLayout {

    private ArrayList<Violation> violationList = new ArrayList<>();
    private Grid<Violation> grid = new Grid<>(Violation.class);

    GridView() {
        grid.setHeightByRows(true);
        add(grid);
    }

    public void update(StardogDatabaseAPI.Result results) {
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

        grid.setItems(violationList);
    }
}
