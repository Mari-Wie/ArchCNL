package org.archcnl.domain.input.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
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
        String conceptsString = constructConceptString(customConcepts);

        List<CustomRelation> customRelations =
                rulesConceptsAndRelations.getRelationManager().getCustomRelations();
        String relationsString = constructRelationString(customRelations);

        FileUtils.writeStringToFile(
                file, rulesString + conceptsString + relationsString, StandardCharsets.UTF_8);
    }

    private String constructArchRuleString(List<ArchitectureRule> rules) {
        StringBuilder builder = new StringBuilder();
        // TODO: Add description once rules have them
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
            if (!concept.getDescription().isEmpty()) {
                builder.append("[role=\"description\"]");
                builder.append("\n");
                builder.append(mapping.getMappingNameRepresentation());
                builder.append(": ");
                builder.append(concept.getDescription());
                builder.append("\n");
            }
            for (String oneMapping : mapping.toStringRepresentation()) {
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
            if (!relation.getDescription().isEmpty()) {
                builder.append("[role=\"description\"]");
                builder.append("\n");
                builder.append(mapping.getMappingNameRepresentation());
                builder.append(": ");
                builder.append(relation.getDescription());
                builder.append("\n");
            }
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
