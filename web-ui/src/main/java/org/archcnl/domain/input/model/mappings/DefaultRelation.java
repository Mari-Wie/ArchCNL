package org.archcnl.domain.input.model.mappings;

import java.util.List;

public class DefaultRelation extends Relation {

    private static final String RELATION_TYPE = "famix";

    public DefaultRelation(String name, List<ObjectType> relatableObjectTypes) {
        super(name, relatableObjectTypes);
    }

    @Override
    public String toStringRepresentation() {
        return RELATION_TYPE + ":" + getName();
    }
}
