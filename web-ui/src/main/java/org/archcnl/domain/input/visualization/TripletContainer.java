package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.CatchesExceptionConnection;
import org.archcnl.domain.input.visualization.connections.ContainmentConnection;
import org.archcnl.domain.input.visualization.connections.DeclaresExceptionConnection;
import org.archcnl.domain.input.visualization.connections.DefinesVariableConnection;
import org.archcnl.domain.input.visualization.connections.ImportConnection;
import org.archcnl.domain.input.visualization.connections.PlantUmlConnection;
import org.archcnl.domain.input.visualization.connections.ThrowsExceptionConnection;

public class TripletContainer {

    private enum ElementProperty {
        hasName,
        hasPath,
        namespaceContains,
        hasFullQualifiedName,
        hasModifier,
        hasAnnotationInstance,
        definesAttribute,
        definesMethod,
        isInterface,
        isConstructor,
        hasDeclaredType,
        definesParameter,
        hasAnnotationTypeAttribute,
        hasAnnotationType,
        hasAnnotationInstanceAttribute,
        hasValue,
        inheritsFrom;
    }

    private enum ElementConnection {
        containsArtifact(ContainmentConnection::new),
        // isExternal,
        // isLocatedAt,
        imports(ImportConnection::new),
        definesNestedType(ContainmentConnection::new),
        // hasSignature,
        definesVariable(DefinesVariableConnection::new),
        throwsException(ThrowsExceptionConnection::new),
        hasCaughtException(CatchesExceptionConnection::new),
        hasDeclaredException(DeclaresExceptionConnection::new);

        private BiFunction<Variable, Variable, PlantUmlConnection> creator;

        private ElementConnection(BiFunction<Variable, Variable, PlantUmlConnection> creator) {
            this.creator = creator;
        }
    }

    private List<Triplet> elementPropertyTriplets;
    private List<Triplet> elementConnectionTriplets;

    public TripletContainer(List<Triplet> triplets) {
        setElementPropertyTriplets(triplets);
        setElementRelationTriplets(triplets);
    }

    private void setElementPropertyTriplets(List<Triplet> triplets) {
        elementPropertyTriplets =
                triplets.stream()
                        .filter(t -> isElementProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setElementRelationTriplets(List<Triplet> triplets) {
        elementConnectionTriplets =
                triplets.stream()
                        .filter(t -> isElementConnection(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private boolean isElementProperty(Relation relation) {
        return EnumUtils.isValidEnum(ElementProperty.class, relation.getName());
    }

    private boolean isElementConnection(Relation relation) {
        return EnumUtils.isValidEnum(ElementConnection.class, relation.getName());
    }

    public List<Triplet> getElementPropertyTriplets() {
        return elementPropertyTriplets;
    }

    public List<Triplet> getElementConnectionTriplets() {
        return elementConnectionTriplets;
    }
}
