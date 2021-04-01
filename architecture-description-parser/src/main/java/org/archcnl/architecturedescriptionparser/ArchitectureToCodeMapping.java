package org.archcnl.architecturedescriptionparser;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ArchitectureToCodeMapping {
    private String content;

    public ArchitectureToCodeMapping(String namespacePrefix, List<String> swrlRules) {
        content = namespacePrefix;

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
}
