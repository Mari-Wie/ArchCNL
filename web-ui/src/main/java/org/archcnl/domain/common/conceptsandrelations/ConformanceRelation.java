package org.archcnl.domain.common.conceptsandrelations;

import java.util.List;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;

public class ConformanceRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "conformance";

    public ConformanceRelation(
            String name, String description, List<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getName();
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }
}
