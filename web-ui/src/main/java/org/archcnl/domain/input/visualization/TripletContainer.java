package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;

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
        hasDeclaredType,
        definesParameter,
        hasAnnotationTypeAttribute,
        hasAnnotationType,
        hasAnnotationInstanceAttribute,
        hasValue,
        inheritsFrom;
    }

    private enum ElementRelation {
        containsArtifact,
        isExternal,
        isLocatedAt,
        imports,
        definesNestedType,
        hasSignature,
        isConstructor,
        definesVariable,
        throwsException,
        hasCaughtException,
        hasDeclaredException;

        private BiFunction<Variable, Variable, PlantUmlElement> creator;

        private ElementRelation(BiFunction<Variable, Variable, PlantUmlElement> creator) {
            this.creator = creator;
        }
    }

    private List<Triplet> elementPropertyTriplets;
    private List<Triplet> elementRelationTriplets;

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
        elementRelationTriplets =
                triplets.stream()
                        .filter(t -> isElementRelation(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private boolean isElementProperty(Relation relation) {
        return EnumUtils.isValidEnum(ElementProperty.class, relation.getName());
    }

    private boolean isElementRelation(Relation relation) {
        return EnumUtils.isValidEnum(ElementRelation.class, relation.getName());
    }

    public List<Triplet> getElementPropertyTriplets() {
        return elementPropertyTriplets;
    }

    public List<Triplet> getElementRelationTriplets() {
        return elementRelationTriplets;
    }
}
