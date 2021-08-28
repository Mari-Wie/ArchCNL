package org.archcnl.commons.exceptions;

public class PropertyNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  public PropertyNotFoundException(final String propertyName) {
    super("Property with name '" + propertyName + "' not found.");
  }
}
