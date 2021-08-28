package org.archcnl.output.model.query;

public interface FormattedDomainObject {

  /**
   * Return prepared string representation of an SPARQL element.
   * 
   * For example: PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
   * 
   * @return prepared string representation of an SPARQL element
   */
  public String asFormattedString();
}
