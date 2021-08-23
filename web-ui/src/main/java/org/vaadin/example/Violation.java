package org.vaadin.example;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

public class Violation {
    TreeMap<String, String> violation = new TreeMap<String, String>();

    Violation(List<String> vars, ArrayList<String> values) {
        for (int i = 0; i < vars.size(); i++) {
            violation.put(vars.get(i), values.get(i));
        }
    }

    public String getVal(String key) {
        return violation.get(key);
    }
}
