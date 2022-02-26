package org.archcnl.domain.common.conceptsandrelations;

import java.util.Set;

import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;

public class FamixRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "famix";

    public FamixRelation(
            String name, String description, Set<ActualObjectType> relatableSubjectTypes, Set<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableSubjectTypes, relatableObjectTypes);
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getName();
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }
}
