package org.archcnl.domain.input.visualization.diagram.connections;

public class ContainmentConnection extends PlantUmlConnection {

    public ContainmentConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.CONTAINMENT);
    }
}
