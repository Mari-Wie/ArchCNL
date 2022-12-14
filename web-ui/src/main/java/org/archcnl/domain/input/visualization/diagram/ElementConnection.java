package org.archcnl.domain.input.visualization.diagram;

import java.util.function.BiFunction;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.input.visualization.diagram.connections.CatchesExceptionConnection;
import org.archcnl.domain.input.visualization.diagram.connections.ContainmentConnection;
import org.archcnl.domain.input.visualization.diagram.connections.DeclaresExceptionConnection;
import org.archcnl.domain.input.visualization.diagram.connections.DefinesVariableConnection;
import org.archcnl.domain.input.visualization.diagram.connections.ImportConnection;
import org.archcnl.domain.input.visualization.diagram.connections.IsAConnection;
import org.archcnl.domain.input.visualization.diagram.connections.PlantUmlConnection;
import org.archcnl.domain.input.visualization.diagram.connections.ThrowsExceptionConnection;

public enum ElementConnection {
    // isExternal, isLocatedAt, and hasSignature are not supported
    imports(ImportConnection::new),
    definesNestedType(ContainmentConnection::new),
    definesVariable(DefinesVariableConnection::new),
    throwsException(ThrowsExceptionConnection::new),
    hasCaughtException(CatchesExceptionConnection::new),
    hasDeclaredException(DeclaresExceptionConnection::new),
    isA(IsAConnection::new);

    private BiFunction<String, String, PlantUmlConnection> creator;

    private ElementConnection(BiFunction<String, String, PlantUmlConnection> creator) {
        this.creator = creator;
    }

    public PlantUmlConnection createConnection(String subjectId, String objectId) {
        return creator.apply(subjectId, objectId);
    }

    public static boolean isElementConnection(Relation relation) {
        return EnumUtils.isValidEnum(ElementConnection.class, relation.getName());
    }
}
