package org.archcnl.domain.common;

/** Domain object that has pretty representation for query to Stardog, ADOC files and GUI. */
public interface FormattedDomainObject
        extends FormattedQueryDomainObject, FormattedViewDomainObject, FormattedAdocDomainObject {}
