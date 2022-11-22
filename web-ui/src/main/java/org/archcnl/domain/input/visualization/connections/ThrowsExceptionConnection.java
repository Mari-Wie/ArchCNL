package org.archcnl.domain.input.visualization.connections;

public class ThrowsExceptionConnection extends PlantUmlConnection {

    public ThrowsExceptionConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.DASHED);
        setName("<<throws>>");
    }
}
