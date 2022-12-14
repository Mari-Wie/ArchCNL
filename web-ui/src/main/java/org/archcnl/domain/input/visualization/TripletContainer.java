package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.coloredmodel.ColorState;
import org.archcnl.domain.input.visualization.coloredmodel.ColoredTriplet;
import org.archcnl.domain.input.visualization.diagram.ElementConnection;
import org.archcnl.domain.input.visualization.diagram.ElementProperty;
import org.archcnl.domain.input.visualization.diagram.PlantUmlBlock;
import org.archcnl.domain.input.visualization.diagram.PlantUmlPart;
import org.archcnl.domain.input.visualization.diagram.connections.CustomRelationConnection;
import org.archcnl.domain.input.visualization.diagram.connections.PlantUmlConnection;
import org.archcnl.domain.input.visualization.diagram.elements.BooleanElement;
import org.archcnl.domain.input.visualization.diagram.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.diagram.elements.StringElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;
import org.archcnl.domain.input.visualization.visualizers.mappings.ConceptVisualizer;

public class TripletContainer {

    private List<ColoredTriplet> elementPropertyTriplets = new ArrayList<>();
    private List<ColoredTriplet> connectionTriplets = new ArrayList<>();

    public TripletContainer(List<ColoredTriplet> triplets)
            throws MappingToUmlTranslationFailedException {
        for (ColoredTriplet triplet : triplets) {
            if (ElementProperty.isElementProperty(triplet.getPredicate())) {
                elementPropertyTriplets.add(triplet);
            } else if (isElementConnectionOrCustomConnectionTriplet(triplet)) {
                connectionTriplets.add(triplet);
            } else if (isNotTypeTriplet(triplet)) {
                throw new MappingToUmlTranslationFailedException(triplet + " couldn't be sorted");
            }
        }
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

    private boolean isNotTypeTriplet(Triplet triplet) {
        return !triplet.getPredicate().equals(TypeRelation.getTyperelation());
    }
}
