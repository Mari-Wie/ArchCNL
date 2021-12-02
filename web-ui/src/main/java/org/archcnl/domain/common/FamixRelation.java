package org.archcnl.domain.common;

import java.util.List;

public class FamixRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "famix";

    public FamixRelation(
            String name, String description, List<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getName();
    }
}
