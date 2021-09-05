package org.archcnl.domain.input.datatypes.mappings;

public class DefaultConcept extends Concept {

    private static final String CONCEPT_TYPE = "famix";

    public DefaultConcept(String name) {
        super(name);
    }

    @Override
    public String toStringRepresentation() {
        return CONCEPT_TYPE + ":" + getName();
    }
}
