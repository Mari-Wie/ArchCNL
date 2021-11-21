package org.archcnl.domain.common;

/** Domain object that has pretty representation for ADOC format. */
public interface FormattedAdocDomainObject {

    /**
     * Return prepared for query string representation of an ADOC element.
     *
     * @return prepared string representation for ADOC file
     */
    public String transformToAdoc();
}
