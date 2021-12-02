package org.archcnl.domain.common;

public class ConformanceConcept extends Concept {

    private static final String CONCEPT_TYPE = "conformance";

    protected ConformanceConcept(String name, String description) {
        super(name, description);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
