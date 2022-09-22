package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ThrowsExceptionConnection extends PlantUmlConnection {

    public ThrowsExceptionConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.DASHED);
        setName("<<throwsException>>");
    }
}
