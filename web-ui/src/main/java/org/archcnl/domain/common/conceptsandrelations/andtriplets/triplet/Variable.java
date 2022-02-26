package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Variable extends ObjectType {

    private String name;
    private Set<ActualObjectType> dynamicTypes;

    public Variable(String name) {
        this.name = name;
        this.dynamicTypes = new LinkedHashSet<>();
    }

    public Set<ActualObjectType> getDynamicType() {
        return dynamicTypes;
    }

    public void clearDynamicTypes() {
        dynamicTypes.clear();
    }

    public void addDynamicType(ActualObjectType dynamicType) {
        dynamicTypes.add(dynamicType);
    }

    @Override
    public String getName() {
        return name;
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
        return "?" + name;
    }

    @Override
    protected boolean requiredEqualsOverride(Object obj) {
        if (obj instanceof Variable) {
            final Variable that = (Variable) obj;
            return Objects.equals(this.getName(), that.getName());
        }
        return false;
    }

    @Override
    protected int requiredHashCodeOverride() {
        return Objects.hash(name);
    }
}
