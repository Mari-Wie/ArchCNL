package org.archcnl.output.model.query;

/**
 * Representation of standard for ArchCNL namespaces in SPARQL query.
 */
public enum QueryNamesapace implements FormattedQueryDomainObject, FormattedViewDomainObject {

  RDF("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"),

  OWL("owl", "http://www.w3.org/2002/07/owl#"),

  RDFS("rdfs", "http://www.w3.org/2000/01/rdf-schema#"),

  XSD("xsd", "http://www.w3.org/2001/XMLSchema#"),

  CONFORMANCE("conformance", "http://arch-ont.org/ontologies/architectureconformance#"),

  FAMIX("famix", "http://arch-ont.org/ontologies/famix.owl#"),

  ARCHITECTURE("architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");

  private String alias;
  private String uri;

  private QueryNamesapace(final String alias, final String uri) {
    this.alias = alias;
    this.uri = uri;
  }

  public String getAlias() {
    return alias;
  }

  public String getUri() {
    return uri;
  }

  @Override
  public String asFormattedString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("PREFIX ");
    sb.append(getAlias());
    sb.append(" <");
    sb.append(getUri());
    sb.append(">");
    return sb.toString();
  }

  @Override
  public String asFormattedQuery() {
    return asFormattedString();
  }
}
