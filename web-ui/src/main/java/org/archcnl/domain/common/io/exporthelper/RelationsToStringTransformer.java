package org.archcnl.domain.common.io.exporthelper;

import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.input.model.mappings.Mapping;

public class RelationsToStringTransformer {

    private RelationsToStringTransformer() {}

    public static String constructRelationString(List<CustomRelation> relations) {
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
