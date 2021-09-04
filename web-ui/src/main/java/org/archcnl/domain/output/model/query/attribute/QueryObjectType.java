package org.archcnl.domain.output.model.query.attribute;

/** Type of object. */
public enum QueryObjectType {
    /** Like a SPARQL query field, for example: ?name */
    FIELD,
    /** XSD primitive type, for exapmle: xsd:string, xsd:boolean, xsd:integer etc */
    PRIMITIVE_VALUE,
    /** Property, for exapmle: architecture:Aggregate */
    PROPERTY;
}
