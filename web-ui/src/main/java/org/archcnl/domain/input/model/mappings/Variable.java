package org.archcnl.domain.input.model.mappings;

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

    @Override
    /** Warning: Not a real equals method! Only checks if o is instance of this class. */
    public boolean equals(Object o) {
        return o instanceof Variable;
    }

    @Override
    /** Warning: Not a real hasCode method! Will always return 0. */
    public int hashCode() {
        return 0;
    }
}
