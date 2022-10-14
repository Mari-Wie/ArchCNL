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
import org.archcnl.domain.input.visualization.elements.CustomConceptVisualizer;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.MultipleBaseElementsException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

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

    private List<Triplet> elementPropertyTriplets;
    private List<Triplet> elementConnectionTriplets;
    private List<Triplet> customRelationConnectionTriplets;

    public TripletContainer(List<Triplet> triplets) {
        setElementPropertyTriplets(triplets);
        setElementRelationTriplets(triplets);
        setCustomRelationConnectionTriplets(triplets);
    }

    public void applyElementProperties(Map<Variable, PlantUmlBlock> elementMap)
            throws MappingToUmlTranslationFailedException {
        for (Triplet triplet : elementPropertyTriplets) {
            Variable subject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType object = triplet.getObject();
            PlantUmlBlock subjectElement = elementMap.get(subject);
            if (object instanceof Variable) {
                PlantUmlBlock objectElement = elementMap.get(object);
                tryToSetProperty(subjectElement, predicate.getName(), objectElement);
            } else if (object instanceof StringValue) {
                String objectString = ((StringValue) object).getValue();
                tryToSetProperty(subjectElement, predicate.getName(), objectString);
            } else {
                boolean objectBool = ((BooleanValue) object).getValue();
                tryToSetProperty(subjectElement, predicate.getName(), objectBool);
            }
        }
    }

    public List<PlantUmlConnection> createConnections(Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlConnection> connections = createElementConnections(elementMap);
        connections.addAll(createCustomRelationConnection(elementMap));
        return connections;
    }

    private List<PlantUmlConnection> createElementConnections(
            Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlConnection> connections = new ArrayList<>();
        for (Triplet triplet : elementConnectionTriplets) {
            String key = triplet.getPredicate().getName();
            ElementConnection enumEntry = ElementConnection.valueOf(key);

            Variable subject = triplet.getSubject();
            List<String> subjectIds = elementMap.get(subject).getIdentifier();
            // TODO allow non-variables as objects
            Variable object = (Variable) triplet.getObject();
            List<String> objectIds = elementMap.get(object).getIdentifier();

            for (String subjectId : subjectIds) {
                for (String objectId : objectIds) {
                    PlantUmlConnection connection = enumEntry.createConnection(subjectId, objectId);
                    connections.add(connection);
                }
            }
        }
        return connections;
    }

    private List<PlantUmlConnection> createCustomRelationConnection(
            Map<Variable, PlantUmlBlock> elementMap) {
        List<PlantUmlConnection> connections = new ArrayList<>();
        for (Triplet triplet : customRelationConnectionTriplets) {
            CustomRelation predicate = (CustomRelation) triplet.getPredicate();

            Variable subject = triplet.getSubject();
            List<String> subjectIds = elementMap.get(subject).getIdentifier();
            // TODO allow non-variables as objects
            Variable object = (Variable) triplet.getObject();
            List<String> objectIds = elementMap.get(object).getIdentifier();

            for (String subjectId : subjectIds) {
                for (String objectId : objectIds) {
                    CustomRelationConnection connection =
                            new CustomRelationConnection(subjectId, objectId, predicate);
                    connections.add(connection);
                }
            }
        }
        return connections;
    }

    private void tryToSetProperty(PlantUmlBlock element, String property, Object object)
            throws MappingToUmlTranslationFailedException {
        try {
            if (object instanceof CustomConceptVisualizer) {
                object = ((CustomConceptVisualizer) object).getBaseElement();
            }
            element.setProperty(property, object);
        } catch (PropertyNotFoundException | MultipleBaseElementsException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private void setElementPropertyTriplets(List<Triplet> triplets) {
        elementPropertyTriplets =
                triplets.stream()
                        .filter(t -> ElementProperty.isElementProperty(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setElementRelationTriplets(List<Triplet> triplets) {
        elementConnectionTriplets =
                triplets.stream()
                        .filter(t -> ElementConnection.isElementConnection(t.getPredicate()))
                        .collect(Collectors.toList());
    }

    private void setCustomRelationConnectionTriplets(List<Triplet> triplets) {
        customRelationConnectionTriplets =
                triplets.stream()
                        .filter(t -> t.getPredicate() instanceof CustomRelation)
                        .collect(Collectors.toList());
    }
}
