package org.archcnl.domain.input.visualization.connections;

public class IsAConnection extends PlantUmlConnection {

    public IsAConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.INHERITS);
        setName("Is-a");
    }
}
