package org.archcnl.domain.common;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class VariableManager {

    private Set<Variable> variables;

    public VariableManager() {
        variables = new LinkedHashSet<>();
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public Optional<Variable> getVariableByName(String name) {
        return variables.stream().filter(variable -> name.equals(variable.getName())).findAny();
    }

    public boolean doesVariableExist(Variable variable) {
        return variables.stream()
                .anyMatch(
                        existingVariable -> variable.getName().equals(existingVariable.getName()));
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    private void clearAllDynamicTypes() {
        variables.forEach(Variable::clearDynamicTypes);
    }

    /**
     * Checks whether potentially conflicting dynamic types exist Parses and sets dynamic types for
     * all Variables in the VariableManager. Any missing variables from the mapping will be added to
     * this manager.
     *
     * @param andTriplets The triplets whose syntax will be checked
     * @return true if there are conflicting dynamic types
     */
    public boolean hasConflictingDynamicTypes(
            AndTriplets andTriplets, ConceptManager conceptManager) {
        parseVariableTypes(andTriplets, conceptManager);
        return variables.stream().anyMatch(Variable::hasConflictingDynamicTypes);
    }

    public List<Variable> getConflictingVariables(
            AndTriplets andTriplets, ConceptManager conceptManager) {
        parseVariableTypes(andTriplets, conceptManager);
        return variables.stream()
                .filter(Variable::hasConflictingDynamicTypes)
                .collect(Collectors.toList());
    }

    public void parseVariableTypes(AndTriplets andTriplets, ConceptManager conceptManager) {
        clearAllDynamicTypes();
        for (Triplet triplet : andTriplets.getTriplets()) {
            if (triplet.getPredicate() instanceof TypeRelation) {
                handleTypeRelationTriplet(triplet, conceptManager);
            } else {
                handleTripletWithoutTypeRelation(triplet, conceptManager);
            }
        }
    }

    private void handleTypeRelationTriplet(Triplet triplet, ConceptManager conceptManager) {
        // The TypeRelation can only relate to Concepts
        Concept object = (Concept) triplet.getObject();
        Set<ActualObjectType> dynamicTypes = new LinkedHashSet<>(Arrays.asList(object));

        if (object instanceof CustomConcept) {
            CustomConcept concept = (CustomConcept) object;
            Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
            dynamicTypes.addAll(baseTypes);
        }

        refineAndAddVariable(triplet.getSubject(), dynamicTypes, conceptManager);
    }

    private void handleTripletWithoutTypeRelation(Triplet triplet, ConceptManager conceptManager) {
        // handle subject
        Variable subject = triplet.getSubject();
        Relation predicate = triplet.getPredicate();
        ObjectType object = triplet.getObject();
        refineAndAddVariable(subject, predicate.getRelatableSubjectTypes(), conceptManager);

        // handle object
        if (object instanceof Variable) {
            refineAndAddVariable(
                    (Variable) object, predicate.getRelatableObjectTypes(), conceptManager);
        }
    }

    private void refineAndAddVariable(
            Variable variable, Set<ActualObjectType> dynamicTypes, ConceptManager conceptManager) {
        Optional<Variable> variableFromManager = getVariableByName(variable.getName());
        if (variableFromManager.isEmpty()) {
            variable.clearDynamicTypes();
            addVariable(variable);
            variable.refineDynamicTypes(dynamicTypes, conceptManager);
        } else {
            variableFromManager.get().refineDynamicTypes(dynamicTypes, conceptManager);
        }
    }
}
