package org.archcnl.domain.input.visualization.diagram.connections;

public class ImportConnection extends PlantUmlConnection {

    public ImportConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.DASHED);
        setName("<<imports>>");
    }
}
