package org.archcnl.domain.input.visualization.connections;

public class CatchesExceptionConnection extends PlantUmlConnection {

    public CatchesExceptionConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.DASHED);
        setName("<<catches>>");
    }
}
