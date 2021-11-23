package org.archcnl.domain.common;

import java.util.List;

public class JenaBuiltinRelation extends Relation {

    private String realName;

    public JenaBuiltinRelation(
            String name,
            String realName,
            String description,
            List<ObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
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
        return getRealName();
    }
}
