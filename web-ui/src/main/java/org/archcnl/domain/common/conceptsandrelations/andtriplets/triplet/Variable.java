package org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet;

import java.util.Objects;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.exceptions.InvalidVariableNameException;

public class Variable extends ObjectType {

    private String name;

    public Variable(String name) throws InvalidVariableNameException {
        if (name.startsWith("?")) {
            name = name.substring(1);
        }
        if (!name.matches("\\w+")) {
            throw new InvalidVariableNameException(name);
        }
        this.name = name;
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
