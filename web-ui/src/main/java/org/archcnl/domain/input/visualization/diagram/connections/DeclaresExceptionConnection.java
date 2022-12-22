package org.archcnl.domain.input.visualization.diagram.connections;

public class DeclaresExceptionConnection extends PlantUmlConnection {

    public DeclaresExceptionConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.DASHED);
        setName("<<declaresException>>");
    }
}
