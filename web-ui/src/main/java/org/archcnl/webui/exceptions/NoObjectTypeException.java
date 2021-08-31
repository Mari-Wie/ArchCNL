package org.archcnl.webui.exceptions;

public class NoObjectTypeException extends Exception {

	private static final long serialVersionUID = 1908475977768328672L;
	
	public NoObjectTypeException(String potentialObject) {
		super("The string \"" + potentialObject + "\" could not be parsed to an object.");
	}

}
