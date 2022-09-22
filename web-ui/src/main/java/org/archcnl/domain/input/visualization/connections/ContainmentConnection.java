package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ContainmentConnection extends PlantUmlConnection {

    public ContainmentConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.CONTAINMENT);
    }
}
