package org.archcnl.domain.input.datatypes.mappings;

import org.archcnl.domain.input.exceptions.InvalidVariableNameException;

public class Variable {

    private String name;

    public Variable(String name) throws InvalidVariableNameException {
        if (!name.matches("\\w+")) {
            throw new InvalidVariableNameException(name);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toStringRepresentation() {
        return "?" + name;
    }

    public boolean sameNameAs(Variable variable) {
        return name.equals(variable.getName());
    }
}
