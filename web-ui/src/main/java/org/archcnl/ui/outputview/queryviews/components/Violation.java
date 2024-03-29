package org.archcnl.ui.outputview.queryviews.components;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Violation {
    Map<String, String> violation = new TreeMap<String, String>();

    public Violation(final List<String> vars, final List<String> values) {
        for (int i = 0; i < vars.size(); i++) {
            String value = URLDecoder.decode(values.get(i), StandardCharsets.UTF_8);
            violation.put(vars.get(i), value);
        }
    }

    public String getVal(final String key) {
        return violation.get(key);
    }
}
