package org.archcnl.domain.common;

import java.util.Objects;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;

public class Variable extends ObjectType implements FormattedDomainObject {

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
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Variable) {
            final Variable that = (Variable) obj;
            return Objects.equals(this.getName(), that.getName());
        }
        return false;
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
}
