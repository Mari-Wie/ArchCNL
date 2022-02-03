package org.archcnl.domain.common.conceptsandrelations;

import java.util.LinkedList;
import org.archcnl.domain.common.FormattedQueryDomainObject;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ObjectType;

public class TypeRelation extends Relation implements FormattedQueryDomainObject {

    private static final String RELATION_TYPE = "rdf";

    private String realName;

    public TypeRelation(String name, String realName, String description) {
        super(name, description, new LinkedList<>());
        this.realName = realName;
    }

    @Override
    public boolean canRelateToObjectType(ObjectType objectType) {
        return objectType instanceof Concept;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public String transformToGui() {
        return transformToAdoc();
    }

    @Override
    public String transformToAdoc() {
        return RELATION_TYPE + ":" + getRealName();
    }

    @Override
    public String transformToSparqlQuery() {
        return transformToAdoc();
    }
}
