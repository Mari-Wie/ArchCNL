package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class DefinesVariableConnection extends PlantUmlConnection {

    public DefinesVariableConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.COMPOSITION);
        setName("definesVariable");
    }
}
