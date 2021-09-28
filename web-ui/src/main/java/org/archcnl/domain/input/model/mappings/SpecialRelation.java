package org.archcnl.domain.input.model.mappings;

import java.util.List;

public class SpecialRelation extends Relation {

    private String realName;

    public SpecialRelation(String name, String realName, List<ObjectType> relatableObjectTypes) {
        super(name, relatableObjectTypes);
        this.realName = realName;
    }

    @Override
    public String toStringRepresentation() {
        return getRealName();
    }

    public String getRealName() {
        return realName;
    }
}