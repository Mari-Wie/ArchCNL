package org.archcnl.domain.input.visualization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.FamixConcept;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.input.visualization.elements.PlantUmlElement;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class MappingTranslator {

    private List<Triplet> whenTriplets;
    private Triplet thenTriplet;

    public MappingTranslator(AndTriplets andTriplets, Triplet thenTriplet) {
        this.whenTriplets = andTriplets.getTriplets();
        this.thenTriplet = thenTriplet;
    }

    public void translateToPlantUmlModel(ConceptManager conceptManager)
            throws MappingToUmlTranslationFailedException {
        Set<Variable> variables = prepareMappingForTranslation(conceptManager);
        Map<Variable, PlantUmlElement> elementMap = createPlantUmlModels(variables);
        TripletContainer container = new TripletContainer(whenTriplets);
        applyDataProperties(container, elementMap);
        // apply relations between objects
        // set thenTriplet note
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

    private void applyDataProperties(
            TripletContainer container, Map<Variable, PlantUmlElement> elementMap) {
        // TODO Auto-generated method stub
    }
}
