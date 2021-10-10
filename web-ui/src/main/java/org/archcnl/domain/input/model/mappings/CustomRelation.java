package org.archcnl.domain.input.model.mappings;

import java.util.LinkedList;
import java.util.Optional;
import org.archcnl.domain.input.exceptions.UnrelatedMappingException;

public class CustomRelation extends Relation {

    private static final String RELATION_TYPE = "architecture";

    private RelationMapping mapping;

    public CustomRelation(String name) {
        super(name, new LinkedList<>());
    }

    public void setMapping(RelationMapping mapping) throws UnrelatedMappingException {
        if (this.equals(mapping.getThenTriplet().getPredicate())) {
            this.mapping = mapping;
            addRelatableObjectType(mapping.getThenTriplet().getObject());
        } else {
            throw new UnrelatedMappingException(
                    getName(), mapping.getThenTriplet().getPredicate().getName());
        }
    }

    public Optional<RelationMapping> getMapping() {
        return Optional.ofNullable(mapping);
    }

    @Override
    public String toStringRepresentation() {
        return RELATION_TYPE + ":" + getName();
    }
}
