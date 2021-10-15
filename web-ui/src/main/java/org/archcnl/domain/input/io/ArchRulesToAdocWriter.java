package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.CustomConcept;
import org.archcnl.domain.input.model.mappings.CustomRelation;
import org.archcnl.domain.input.model.mappings.Mapping;

public class ArchRulesToAdocWriter implements ArchRulesExporter {

    @Override
    public void writeArchitectureRules(
            File file, RulesConceptsAndRelations rulesConceptsAndRelations) throws IOException {
        String rulesString =
                constructArchRuleString(
                        rulesConceptsAndRelations
                                .getArchitectureRuleManager()
                                .getArchitectureRules());

        List<CustomConcept> customConcepts =
                rulesConceptsAndRelations.getConceptManager().getCustomConcepts();
        List<Mapping> conceptMappings = new LinkedList<>();
        customConcepts.forEach(
                concept -> concept.getMapping().ifPresent(mapping -> conceptMappings.add(mapping)));
        String conceptMappingsString = constructMappingString(conceptMappings);

        List<CustomRelation> customRelations =
                rulesConceptsAndRelations.getRelationManager().getCustomRelations();
        List<Mapping> relationMappings = new LinkedList<>();
        customRelations.forEach(
                relation ->
                        relation.getMapping().ifPresent(mapping -> relationMappings.add(mapping)));
        String relationMappingsString = constructMappingString(relationMappings);

        FileUtils.writeStringToFile(
                file,
                rulesString + conceptMappingsString + relationMappingsString,
                StandardCharsets.UTF_8);
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
