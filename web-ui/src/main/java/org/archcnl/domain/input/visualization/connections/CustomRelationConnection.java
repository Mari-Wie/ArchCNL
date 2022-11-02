package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.CustomRelation;

public class CustomRelationConnection extends PlantUmlConnection {

    public CustomRelationConnection(String subjectId, String objectId, CustomRelation relation) {
        super(subjectId, objectId, ArrowType.CUSTOM_RELATION);
        setNoteText(relation.getName());
    }
}
