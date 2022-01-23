package org.archcnl.domain.common.conceptsandrelations;

public class ConformanceConcept extends Concept {

    private static final String CONCEPT_TYPE = "conformance";

    public ConformanceConcept(String name, String description) {
        super(name, description);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
