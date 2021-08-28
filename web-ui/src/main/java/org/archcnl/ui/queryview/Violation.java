package org.archcnl.ui.queryview;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Violation {
    TreeMap<String, String> violation = new TreeMap<String, String>();

    public Violation(List<String> vars, ArrayList<String> values) {
        for (int i = 0; i < vars.size(); i++) {
            violation.put(vars.get(i), values.get(i));
        }
    }

    public String getVal(String key) {
        return violation.get(key);
    }
}
