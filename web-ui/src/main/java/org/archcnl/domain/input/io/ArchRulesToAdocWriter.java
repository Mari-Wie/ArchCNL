package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.common.CustomConcept;
import org.archcnl.domain.common.CustomRelation;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
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
        String conceptMappingsString = constructConceptString(customConcepts);

        List<CustomRelation> customRelations =
                rulesConceptsAndRelations.getRelationManager().getCustomRelations();
        List<Mapping> relationMappings = new LinkedList<>();
        customRelations.forEach(
                relation ->
                        relation.getMapping().ifPresent(mapping -> relationMappings.add(mapping)));
        String relationMappingsString = constructRelationString(customRelations);

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
    
    private String constructConceptString(List<CustomConcept> concepts) {
        StringBuilder builder = new StringBuilder();
        concepts.removeIf(concept -> concept.getMapping().isEmpty());
        for (CustomConcept concept : concepts) {
        	Mapping mapping = concept.getMapping().get();
            for (String oneMapping : mapping.toStringRepresentation()) {
            	builder.append("[role=\"description\"]");
                builder.append("\n");
                builder.append(concept.getDescription());
                builder.append("\n");
                builder.append("[role=\"mapping\"]");
                builder.append("\n");
                builder.append(oneMapping);
                builder.append("\n\n");
            }
        }
        return builder.toString();
    }
    
    private String constructRelationString(List<CustomRelation> relations) {
        StringBuilder builder = new StringBuilder();
        relations.removeIf(relation -> relation.getMapping().isEmpty());
        for (CustomRelation relation : relations) {
        	Mapping mapping = relation.getMapping().get();
            for (String oneMapping : mapping.toStringRepresentation()) {
                builder.append("[role=\"mapping\"]");
                builder.append("\n");
                builder.append(oneMapping);
                builder.append("\n");
                builder.append(relation.getDescription());
                builder.append("\n\n");
            }
        }
        return builder.toString();
    }
}
