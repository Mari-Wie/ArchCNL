package org.archcnl.domain.input.visualization.elements;

import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.input.visualization.PlantUmlPart;
import org.archcnl.domain.input.visualization.connections.BasicConnection;
import org.archcnl.domain.input.visualization.connections.PlantUmlConnection;

public class CustomConceptPart implements PlantUmlPart {

    private Concept concept;
    private PlantUmlConnection connection;

    public CustomConceptPart(String subjectId, Concept concept) {
        this.concept = concept;
        this.connection = new BasicConnection(concept.getName(), subjectId);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("note ");
        builder.append("\"" + concept.getName() + "\"");
        builder.append(" as ");
        builder.append(concept.getName() + "\n");
        builder.append(connection.buildPlantUmlCode());
        return builder.toString();
    }
}
