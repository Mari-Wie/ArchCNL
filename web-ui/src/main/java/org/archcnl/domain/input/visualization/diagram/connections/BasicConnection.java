package org.archcnl.domain.input.visualization.diagram.connections;

public class BasicConnection extends PlantUmlConnection {

    public BasicConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.BASIC);
    }
}
