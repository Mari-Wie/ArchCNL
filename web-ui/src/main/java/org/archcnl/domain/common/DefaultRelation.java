package org.archcnl.domain.common;

import java.util.List;

public class DefaultRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "famix";

    public DefaultRelation(String name, String description, List<ObjectType> relatableObjectTypes) {
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
