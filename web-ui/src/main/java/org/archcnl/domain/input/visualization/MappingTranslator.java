package org.archcnl.domain.input.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.connections.CustomRelationConnection;
import org.archcnl.domain.input.visualization.elements.CustomConceptPart;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.archcnl.domain.input.visualization.exceptions.PropertyNotFoundException;

public class MappingTranslator {

    private List<Triplet> whenTriplets;
    private Triplet thenTriplet;

    public MappingTranslator(AndTriplets andTriplets, Triplet thenTriplet) {
        this.whenTriplets = andTriplets.getTriplets();
        this.thenTriplet = thenTriplet;
    }

    public List<PlantUmlPart> translateToPlantUmlModel(ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        Set<Variable> variables = prepareMappingForTranslation(conceptManager);
        Map<Variable, PlantUmlElement> elementMap = createPlantUmlModels(variables);
        TripletContainer container = new TripletContainer(whenTriplets);
        applyElementProperties(container, elementMap);
        List<PlantUmlElement> topLevelElements = getTopLevelElements(elementMap);
        topLevelElements = createRequiredParents(topLevelElements);
        List<PlantUmlPart> parts = new ArrayList<>(topLevelElements);
        // apply relations between objects
        // set thenTriplet note
        parts.add(getThenTripletParts());
        return parts;
    }

    private Set<Variable> prepareMappingForTranslation(ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        Set<Variable> variables = inferVariableTypes(conceptManager);
        TripletReducer reducer = new TripletReducer(whenTriplets, variables);
        whenTriplets = reducer.reduce();
        return inferVariableTypes(conceptManager);
    }

    private Set<Variable> inferVariableTypes(ConceptManager manager)
            throws MappingToUmlTranslationFailedException {
        VariableManager variableManager = new VariableManager();
        AndTriplets container = new AndTriplets(whenTriplets);
        if (variableManager.hasConflictingDynamicTypes(container, manager)) {
            throw new MappingToUmlTranslationFailedException(
                    "Variable with conflicting type usage in mapping.");
        }
        return variableManager.getVariables();
    }

    private Map<Variable, PlantUmlElement> createPlantUmlModels(Set<Variable> variables)
            throws MappingToUmlTranslationFailedException {
        Map<Variable, PlantUmlElement> elementMap = new HashMap<>();
        for (Variable variable : variables) {
            Concept elementType = selectRepresentativeElementType(variable.getDynamicTypes());
            PlantUmlElement element = PlantUmlMapper.createElement(elementType, variable);
            elementMap.put(variable, element);
        }
        return elementMap;
    }

    private Concept selectRepresentativeElementType(Set<ActualObjectType> options) {
        if (options.size() == 1) {
            return (Concept) options.iterator().next();
        }
        // Should only occur alongside the relations imports, namespaceContains, definesNestedType,
        // and hasDeclaredType
        // TODO implement better selection
        return new FamixConcept("FamixClass", "");
    }

    private void applyElementProperties(
            TripletContainer container, Map<Variable, PlantUmlElement> elementMap)
            throws MappingToUmlTranslationFailedException {
        for (Triplet triplet : container.getElementPropertyTriplets()) {
            Variable subject = triplet.getSubject();
            Relation predicate = triplet.getPredicate();
            ObjectType object = triplet.getObject();
            PlantUmlElement subjectElement = elementMap.get(subject);
            if (object instanceof Variable) {
                PlantUmlElement objectElement = elementMap.get(object);
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

    private void tryToSetProperty(PlantUmlElement element, String property, Object object)
            throws MappingToUmlTranslationFailedException {
        try {
            element.setProperty(property, object);
        } catch (PropertyNotFoundException e) {
            throw new MappingToUmlTranslationFailedException(e.getMessage());
        }
    }

    private List<PlantUmlElement> getTopLevelElements(Map<Variable, PlantUmlElement> elementMap) {
        return elementMap.values().stream()
                .filter(Predicate.not(PlantUmlElement::hasParentBeenFound))
                .collect(Collectors.toList());
    }

    private List<PlantUmlElement> createRequiredParents(List<PlantUmlElement> topLevelElements) {
        // TODO implement creation
        for (PlantUmlElement element : topLevelElements) {
            if (!element.hasRequiredParent()) {
                throw new RuntimeException(
                        element.buildPlantUmlCode() + " is missing a required parent");
            }
        }
        return topLevelElements;
    }

    private PlantUmlPart getThenTripletParts() {
        if (thenTriplet.getObject() instanceof Concept) {
            Variable subject = thenTriplet.getSubject();
            Concept object = (Concept) thenTriplet.getObject();
            return new CustomConceptPart(subject, object);
        } else {
            Variable subject = thenTriplet.getSubject();
            Relation predicate = thenTriplet.getPredicate();
            // TODO fix unsafe casting
            Variable object = (Variable) thenTriplet.getObject();
            return new CustomRelationConnection(subject, object, predicate);
        }
    }
}
