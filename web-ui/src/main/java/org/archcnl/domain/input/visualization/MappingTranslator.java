package org.archcnl.domain.input.visualization;

import java.util.List;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.VariableManager;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
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
        Set<Variable> variables = inferVariableTypes(conceptManager);
        prepareMappingForTranslation(variables);
        createPlantUmlModelsInOrder(variables);
        // separateRelationsInObjectRelatedOnesAndRelationsBetweenObjects
        // set Object related properties
        // apply relations between objects
        // set thenTriplet note
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

    private void prepareMappingForTranslation(Set<Variable> variables)
            throws MappingToUmlTranslationFailedException {
        TripletReducer reducer = new TripletReducer(whenTriplets, variables);
        whenTriplets = reducer.reduce();
    }

    private void createPlantUmlModelsInOrder(Set<Variable> variables) {
        List<Variable> sortedVariables = sortVariablesBasedOnConceptLevel(variables);
    }

    private List<Variable> sortVariablesBasedOnConceptLevel(Set<Variable> variables) {
        // first remove inheritance variables
        return null;
    }
}
