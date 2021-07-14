package org.archcnl.architecturedescriptionparser;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ArchitectureToCodeMapping {
    private String content;

    public ArchitectureToCodeMapping(
            Map<String, String> ontologyNamespaces, List<String> swrlRules) {
        content = generatePrefix(ontologyNamespaces);

        for (String rule : swrlRules) {
            content += "[" + rule + "]\n";
        }
    }

    public String asSWRL() {
        return content;
    }

    public void write(Writer target) throws IOException {
        target.write(content);
    }

    private String generatePrefix(Map<String, String> ontologyNamespaces) {
        StringBuilder builder = new StringBuilder();

        for (String abbreviation : ontologyNamespaces.keySet()) {
            builder.append("@prefix ");
            builder.append(abbreviation);
            builder.append(": <");
            builder.append(ontologyNamespaces.get(abbreviation));
            builder.append(">\n");
        }

        String res = builder.toString();
        return res;
    }
}
