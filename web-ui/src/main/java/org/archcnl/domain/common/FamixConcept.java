package org.archcnl.domain.common;

public class FamixConcept extends Concept {

    private static final String CONCEPT_TYPE = "famix";

    public FamixConcept(String name, String description) {
        super(name, description);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
