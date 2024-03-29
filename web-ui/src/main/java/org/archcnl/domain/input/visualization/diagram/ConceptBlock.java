package org.archcnl.domain.input.visualization.diagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.diagram.connections.BasicConnection;
import org.archcnl.domain.input.visualization.diagram.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class ConceptBlock implements PlantUmlBlock {

    private CustomConcept concept;
    private String uniqueName;
    private List<BasicConnection> connections = new ArrayList<>();

    public ConceptBlock(CustomConcept concept, String uniqueName) {
        this.concept = concept;
        this.uniqueName = uniqueName;
    }

    public void addConnection(BasicConnection connection) {
        this.connections.add(connection);
    }

    @Override
    public String buildPlantUmlCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("note ");
        builder.append("\"" + concept.getName() + "\"");
        builder.append(" as ");
        builder.append(uniqueName);
        for (BasicConnection connection : connections) {
            builder.append("\n");
            builder.append(connection.buildPlantUmlCode());
        }
        return builder.toString();
    }

    @Override
    public void setProperty(String property, PlantUmlElement object)
            throws PropertyNotFoundException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasParentBeenFound() {
        return false;
    }

    @Override
    public List<String> getIdentifiers() {
        return Arrays.asList(uniqueName);
    }

    @Override
    public PlantUmlBlock createRequiredParentOrReturnSelf() {
        return this;
    }

    @Override
    public void setColorState(ColorState colorState) {
        throw new UnsupportedOperationException();
    }
}
