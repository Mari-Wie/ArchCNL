package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class CustomRelationConnection extends PlantUmlConnection {

    public CustomRelationConnection(Variable subject, Variable object, Relation relation) {
        super(subject, object, ArrowType.CUSTOM_RELATION);
        setNodeText(relation.getName());
    }
}
