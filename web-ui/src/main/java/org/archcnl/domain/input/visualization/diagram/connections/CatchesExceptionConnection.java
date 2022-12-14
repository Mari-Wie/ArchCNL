package org.archcnl.domain.input.visualization.diagram.connections;

public class CatchesExceptionConnection extends PlantUmlConnection {

    public CatchesExceptionConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.DASHED);
        setName("<<catches>>");
    }
}
