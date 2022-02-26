package org.archcnl.domain.common.conceptsandrelations;

import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.ActualObjectType;

public class JenaBuiltinRelation extends Relation {

    private String realName;

    public JenaBuiltinRelation(
            String name,
            String realName,
            String description,
            Set<ActualObjectType> relatableObjectTypes) {
        super(name, description, relatableObjectTypes);
        this.realName = realName;
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
        return getRealName();
    }
}
