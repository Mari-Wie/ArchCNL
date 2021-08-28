package org.archcnl.input.exceptions;

public class NoMappingException extends Exception {

    private static final long serialVersionUID = -5527529574793317234L;

    public NoMappingException(String potentialMapping) {
        super("The string \"" + potentialMapping + "\" could not be parsed to a mapping.");
    }
}
