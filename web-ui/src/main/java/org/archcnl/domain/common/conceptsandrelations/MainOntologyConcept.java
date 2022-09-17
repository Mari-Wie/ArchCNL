package org.archcnl.domain.common.conceptsandrelations;

public class MainOntologyConcept extends Concept {

    private static final String CONCEPT_TYPE = "main";

    public MainOntologyConcept(String name, String description) {
        super(name, description);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
