package org.archcnl.webui.mappings;

import java.util.List;

public abstract class Mapping {

    // The "when" part
    private List<AndTriplets> orStatements;
    private VariableManager variableManager;
    private RelationManager relationManager;
    private ConceptManager conceptManager;

    protected Mapping(
            List<AndTriplets> whenTriplets,
            ConceptManager conceptManager,
            RelationManager relationManager) {
        this.orStatements = whenTriplets;
        this.relationManager = relationManager;
        this.conceptManager = conceptManager;
        this.variableManager = new VariableManager();
    }

    public List<AndTriplets> getWhenTriplets() {
        return orStatements;
    }

    public VariableManager getVariableManager() {
        return variableManager;
    }

    public abstract String getName();

    public abstract Triplet getThenTriplet();

    public RelationManager getRelationManager() {
        return relationManager;
    }

    public ConceptManager getConceptManager() {
        return conceptManager;
    }
}
