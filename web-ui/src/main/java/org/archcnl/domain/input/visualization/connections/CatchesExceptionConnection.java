package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class CatchesExceptionConnection extends PlantUmlConnection {

    public CatchesExceptionConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.DASHED);
        setName("<<catches>>");
    }
}
