package org.archcnl.domain.input.model.mappings;

import java.util.LinkedList;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;
import org.archcnl.domain.input.exceptions.UnsupportedObjectTypeInTriplet;
import org.archcnl.domain.input.exceptions.VariableAlreadyExistsException;

public class CustomConcept extends Concept {

    private static final String CONCEPT_TYPE = "architecture";

    private ConceptMapping mapping;

    public CustomConcept(String name)
            throws VariableAlreadyExistsException, UnsupportedObjectTypeInTriplet,
                    RelationDoesNotExistException, InvalidVariableNameException {
        super(name);
        // TODO: Allow for thenVariable to be null
        mapping = new ConceptMapping(new Variable("placeholder"), new LinkedList<>(), this);
    }

    public void setMapping(ConceptMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getObject())) {
            this.mapping = mapping;
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getObject().getName());
        }
    }

    public ConceptMapping getMapping() {
        return mapping;
    }

    @Override
    public String toStringRepresentation() {
        return CONCEPT_TYPE + ":" + getName();
    }
}