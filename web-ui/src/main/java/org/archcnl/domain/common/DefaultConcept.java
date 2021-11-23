package org.archcnl.domain.common;

public class DefaultConcept extends Concept {

    private static final String CONCEPT_TYPE = "famix";

    public DefaultConcept(String name) {
        super(name);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
