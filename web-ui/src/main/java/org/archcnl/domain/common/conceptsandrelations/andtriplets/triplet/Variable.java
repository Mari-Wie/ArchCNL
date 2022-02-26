package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import java.util.Objects;
import java.util.Optional;

public class Variable extends ObjectType {

    private String name;
    private Optional<ActualObjectType> dynamicType;

    public Variable(String name) {
        this.name = name;
        this.dynamicType = Optional.empty();
    }

    public Optional<ActualObjectType> getDynamicType() {
        return dynamicType;
    }

    protected void setDynamicType(ActualObjectType dynamicType) {
        this.dynamicType = Optional.of(dynamicType);
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
