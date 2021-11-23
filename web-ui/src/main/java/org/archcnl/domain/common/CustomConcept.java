package org.archcnl.domain.common;

import java.util.Optional;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.model.mappings.ConceptMapping;

public class CustomConcept extends Concept {

    private static final String CONCEPT_TYPE = "architecture";

    private ConceptMapping mapping;

    public CustomConcept(String name, String description) {
        super(name, description);
    }

    public CustomConcept(String name) {
        super(name);
    }

    public void setMapping(ConceptMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getObject())) {
            this.mapping = mapping;
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getObject().getName());
        }
    }

    public Optional<ConceptMapping> getMapping() {
        return Optional.ofNullable(mapping);
    }

    @Override
    protected String getConceptType() {
        return CONCEPT_TYPE;
    }
}
