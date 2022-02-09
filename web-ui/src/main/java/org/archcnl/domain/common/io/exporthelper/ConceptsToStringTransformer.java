package org.archcnl.domain.common.io.exporthelper;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.input.model.mappings.Mapping;

public class ConceptsToStringTransformer {

    private ConceptsToStringTransformer() {}

    public static String constructConceptString(List<CustomConcept> concepts) {
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
}
