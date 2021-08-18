package org.archcnl.webui.exceptions;

public class NoTripletException extends Exception {

    private static final long serialVersionUID = 7732314059739224895L;

    public NoTripletException(String potentialTriplet) {
        super("The string \"" + potentialTriplet + "\" could not be parsed to a triplet.");
    }
}
