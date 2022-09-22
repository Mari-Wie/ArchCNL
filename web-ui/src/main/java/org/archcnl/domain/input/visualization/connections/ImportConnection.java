package org.archcnl.domain.input.visualization.connections;

import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class ImportConnection extends PlantUmlConnection {

    public ImportConnection(Variable subject, Variable object) {
        super(subject, object, ArrowType.DASHED);
        setName("<<imports>>");
    }
}
