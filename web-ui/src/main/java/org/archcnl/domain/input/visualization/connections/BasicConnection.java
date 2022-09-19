package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class BasicConnection extends PlantUmlConnection {

    public BasicConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.BASIC);
    }
}
