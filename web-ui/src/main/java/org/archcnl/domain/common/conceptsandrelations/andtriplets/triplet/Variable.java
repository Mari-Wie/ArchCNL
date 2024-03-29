package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;

public class Variable extends ObjectType {

    private String name;
    private Set<ActualObjectType> dynamicTypes;
    private boolean conflictingDynamicTypes;

    public Variable(String name) {
        this.name = name;
        this.dynamicTypes = new HashSet<>();
        this.conflictingDynamicTypes = false;
    }

    public Set<ActualObjectType> getDynamicTypes() {
        return dynamicTypes;
    }

    public void setDynamicTypes(Set<ActualObjectType> dynamicTypes) {
        this.dynamicTypes = new HashSet<>(dynamicTypes);
    }

    public void clearDynamicTypes() {
        dynamicTypes.clear();
        conflictingDynamicTypes = false;
    }

    public void refineDynamicTypes(Set<ActualObjectType> types, ConceptManager conceptManager) {
        if (dynamicTypes.isEmpty()) {
            setDynamicTypes(types);
        } else {
            dynamicTypes.retainAll(addStillValidCustomConcepts(types, conceptManager));
            if (dynamicTypes.isEmpty()) {
                conflictingDynamicTypes = true;
            }
        }
    }

    /** Returns the input set plus any dynamicTypes with base types present in the input set */
    private Set<ActualObjectType> addStillValidCustomConcepts(
            Set<ActualObjectType> types, ConceptManager conceptManager) {
        Set<ActualObjectType> typesWithStillValidOldTypes = new HashSet<>(types);
        for (ActualObjectType type : new HashSet<>(dynamicTypes)) {
            if (type instanceof CustomConcept) {
                CustomConcept concept = (CustomConcept) type;
                Set<ActualObjectType> baseTypes = concept.getBaseTypesFromMapping(conceptManager);
                baseTypes.retainAll(types);
                if (!baseTypes.isEmpty()) {
                    typesWithStillValidOldTypes.add(concept);
                }
            }
        }
        return typesWithStillValidOldTypes;
    }

    public boolean hasConflictingDynamicTypes() {
        return conflictingDynamicTypes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return "?" + name;
    }

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof Variable) {
            final Variable that = (Variable) obj;
            return Objects.equals(this.getName(), that.getName());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(name);
    }
}
