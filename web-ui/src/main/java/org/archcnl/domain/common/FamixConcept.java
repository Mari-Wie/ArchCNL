package org.archcnl.domain.common;

public class FamixConcept extends Concept {

    private static final String CONCEPT_TYPE = "famix";

    public FamixConcept(String name) {
        super(name);
    }

    @Override
    public String toStringRepresentation() {
        return CONCEPT_TYPE + ":" + getName();
    }
}
