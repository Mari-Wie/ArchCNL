package org.archcnl.domain.output.model.query;

/** Domain object that has pretty representation for view. */
public interface FormattedViewDomainObject {

    /**
     * Return prepared for view string representation of an SPARQL element.
     *
     * <p>For example: PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
     *
     * @return prepared string representation for view of an SPARQL element
     */
    public String asFormattedString();
}
