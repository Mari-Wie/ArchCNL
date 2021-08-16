package org.archcnl.webui.exceptions;

public class ConceptDoesNotExistException extends Exception {

	private static final long serialVersionUID = -4157565579564545062L;
	
	public ConceptDoesNotExistException(String name) {
		super("The concept with the name \"" + name + "\" does not exist.");
	}

}
