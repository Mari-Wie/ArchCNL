package org.archcnl.domain.input.datatypes.mappings;

import org.archcnl.domain.input.exceptions.InvalidVariableNameException;

public class Variable extends ObjectType {

    private String name;

    public Variable(String name) throws InvalidVariableNameException {
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
    public String toStringRepresentation() {
        return "?" + name;
    }
}
