package org.archcnl.domain.input.exceptions;

public class NoArchitectureRuleException extends Exception {

    private static final long serialVersionUID = 8373489245252096681L;

    public NoArchitectureRuleException(String potentialRule) {
        super("The string \"" + potentialRule + "\" could not be parsed to an architecture rule.");
    }
}
