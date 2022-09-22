package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class DeclaresExceptionConnection extends PlantUmlConnection {

    public DeclaresExceptionConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.DASHED);
        setName("<<declaresException>>");
    }
}
