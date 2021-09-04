package org.archcnl.ui.output;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Violation {
    Map<String, String> violation = new TreeMap<String, String>();

    public Violation(final List<String> vars, final List<String> values) {
        for (int i = 0; i < vars.size(); i++) {
            violation.put(vars.get(i), values.get(i));
        }
    }

    public String getVal(final String key) {
        return violation.get(key);
    }
}
