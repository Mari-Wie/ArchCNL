package org.archcnl.webui.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.webui.datatypes.RulesAndMappings;
import org.archcnl.webui.datatypes.architecturerules.ArchitectureRule;
import org.archcnl.webui.datatypes.mappings.Mapping;

public class ArchRulesToAdocWriter implements ArchRulesExporter {

    @Override
    public void writeArchitectureRules(File file, RulesAndMappings rulesAndMappings)
            throws IOException {
        String rulesString = constructArchRuleString(rulesAndMappings.getArchitectureRules());
        String mappingsString = constructMappingString(rulesAndMappings.getMappings());
        FileUtils.writeStringToFile(file, rulesString + mappingsString, StandardCharsets.UTF_8);
    }

    private String constructArchRuleString(List<ArchitectureRule> rules) {
        StringBuilder builder = new StringBuilder();
        for (ArchitectureRule rule : rules) {
            builder.append("[role=\"rule\"]");
            builder.append("\n");
            builder.append(rule.toStringRepresentation());
            builder.append("\n\n");
        }
        return builder.toString();
    }

    private String constructMappingString(List<Mapping> mappings) {
        StringBuilder builder = new StringBuilder();
        for (Mapping mapping : mappings) {
            for (String oneMapping : mapping.toStringRepresentation()) {
                builder.append("[role=\"mapping\"]");
                builder.append("\n");
                builder.append(oneMapping);
                builder.append("\n\n");
            }
        }
        return builder.toString();
    }
}
