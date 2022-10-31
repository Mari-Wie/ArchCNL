package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.apache.commons.lang3.EnumUtils;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.CatchesExceptionConnection;
import org.archcnl.domain.input.visualization.connections.ContainmentConnection;
import org.archcnl.domain.input.visualization.connections.CustomRelationConnection;
import org.archcnl.domain.input.visualization.connections.DeclaresExceptionConnection;
import org.archcnl.domain.input.visualization.connections.DefinesVariableConnection;
import org.archcnl.domain.input.visualization.connections.ImportConnection;
import org.archcnl.domain.input.visualization.connections.PlantUmlConnection;
import org.archcnl.domain.input.visualization.connections.ThrowsExceptionConnection;
import org.archcnl.domain.input.visualization.elements.BooleanElement;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.elements.StringElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.mapping.ColorState;
import org.archcnl.domain.input.visualization.mapping.ColoredTriplet;

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

        public static boolean isElementProperty(Relation relation) {
            return EnumUtils.isValidEnum(ElementProperty.class, relation.getName());
        }
    }

    private enum ElementConnection {
        // containsArtifact(ContainmentConnection::new),
        // isExternal,
        // isLocatedAt,
        imports(ImportConnection::new),
        definesNestedType(ContainmentConnection::new),
        // hasSignature,
        definesVariable(DefinesVariableConnection::new),
        throwsException(ThrowsExceptionConnection::new),
        hasCaughtException(CatchesExceptionConnection::new),
        hasDeclaredException(DeclaresExceptionConnection::new);

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

    private List<ColoredTriplet> elementPropertyTriplets;
    private List<ColoredTriplet> connectionTriplets;

    public TripletContainer(List<ColoredTriplet> triplets) {
        setElementPropertyTriplets(triplets);
        setConnectionTriplets(triplets);
    }

    public void applyElementProperties(Map<Variable, PlantUmlBlock> elementMap)
            throws MappingToUmlTranslationFailedException {
        for (ColoredTriplet triplet : elementPropertyTriplets) {
            Variable subject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType object = triplet.getObject();
            PlantUmlBlock subjectElement = elementMap.get(subject);

            updateColorStateWhenNotNeutral(subjectElement, triplet.getColorState());
            if (object instanceof Variable) {
                PlantUmlBlock objectElement = elementMap.get(object);
                updateColorStateWhenNotNeutral(objectElement, triplet.getColorState());
                tryToSetProperty(subjectElement, predicate.getName(), objectElement);
            } else if (object instanceof StringValue) {
                StringElement stringElement = new StringElement((StringValue) object);
                tryToSetProperty(subjectElement, predicate.getName(), stringElement);
            } else {
                BooleanElement boolElement = new BooleanElement((BooleanValue) object);
                tryToSetProperty(subjectElement, predicate.getName(), boolElement);
            }
        }
    }

    public List<PlantUmlConnection> createConnections(Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlConnection> connections = new ArrayList<>();
        for (ColoredTriplet triplet : connectionTriplets) {
            Relation predicate = triplet.getPredicate();
            String key = predicate.getName();

            Variable subject = triplet.getSubject();
            List<String> subjectIds = elementMap.get(subject).getIdentifiers();

            Variable object = (Variable) triplet.getObject();
            List<String> objectIds = elementMap.get(object).getIdentifiers();

            for (String subjectId : subjectIds) {
                for (String objectId : objectIds) {
                    PlantUmlConnection connection;
                    if (predicate instanceof CustomRelation) {
                        CustomRelation relation = (CustomRelation) predicate;
                        connection = new CustomRelationConnection(subjectId, objectId, relation);
                    } else {
                        ElementConnection enumEntry = ElementConnection.valueOf(key);
                        connection = enumEntry.createConnection(subjectId, objectId);
                    }
                    updateColorStateWhenNotNeutral(connection, triplet.getColorState());
                    connection.setCardinality(triplet.getCardinality());
                    connection.setQuantity(triplet.getQuantity());
                    connections.add(connection);
                }
            }
        }
        return connections;
    }

    private void tryToSetProperty(PlantUmlBlock element, String property, PlantUmlBlock object)
            throws MappingToUmlTranslationFailedException {
        try {
            if (object instanceof ConceptVisualizer) {
                ConceptVisualizer visualizer = (ConceptVisualizer) object;
                for (PlantUmlElement baseElement : visualizer.getBaseElements()) {
                    element.setProperty(property, baseElement);
                }
            } else {
                element.setProperty(property, (PlantUmlElement) object);
            }
        } catch (PropertyNotFoundException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private void setElementPropertyTriplets(List<ColoredTriplet> triplets) {
        elementPropertyTriplets =
                triplets.stream()
                        .filter(t -> ElementProperty.isElementProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setConnectionTriplets(List<ColoredTriplet> triplets) {
        connectionTriplets =
                triplets.stream()
                        .filter(this::isElementConnectionOrCustomConnectionTriplet)
                        .collect(Collectors.toList());
    }

    private void updateColorStateWhenNotNeutral(PlantUmlPart block, ColorState state) {
        if (state != ColorState.NEUTRAL) {
            block.setColorState(state);
        }
    }

    private boolean isElementConnectionOrCustomConnectionTriplet(Triplet triplet) {
        Relation predicate = triplet.getPredicate();
        return ElementConnection.isElementConnection(predicate)
                || predicate instanceof CustomRelation;
    }
}
