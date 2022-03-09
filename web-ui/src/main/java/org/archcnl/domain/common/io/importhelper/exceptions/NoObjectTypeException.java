package org.archcnl.domain.common.io.importhelper.exceptions;

public class NoObjectTypeException extends Exception {

    private static final long serialVersionUID = 1908475977768328672L;

    public NoObjectTypeException(String potentialObject) {
        super("The string \"" + potentialObject + "\" could not be parsed to an object.");
    }
}
