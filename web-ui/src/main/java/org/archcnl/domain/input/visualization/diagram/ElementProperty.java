package org.archcnl.domain.input.visualization.diagram;

import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.Relation;

public enum ElementProperty {
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

    public static boolean isElementProperty(Relation relation) {
        return EnumUtils.isValidEnum(ElementProperty.class, relation.getName());
    }
}
