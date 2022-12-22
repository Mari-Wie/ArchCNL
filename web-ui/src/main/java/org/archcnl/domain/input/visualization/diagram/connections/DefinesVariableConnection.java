package org.archcnl.domain.input.visualization.diagram.connections;

public class DefinesVariableConnection extends PlantUmlConnection {

    public DefinesVariableConnection(String subjectId, String objectId) {
        super(subjectId, objectId, ArrowType.COMPOSITION);
        setName("definesVariable");
    }
}
