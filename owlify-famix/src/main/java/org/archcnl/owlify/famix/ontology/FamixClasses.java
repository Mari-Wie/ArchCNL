package org.archcnl.owlify.famix.ontology;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;

public enum FamixClasses {
    Namespace,
    Enum,
    FamixClass,
    AnnotationType,
    AnnotationTypeAttribute,
    AnnotationInstance,
    AnnotationInstanceAttribute,
    Attribute,
    Method,
    DeclaredException,
    Parameter,
    CaughtException,
    ThrownException,
    LocalVariable,
    Inheritance;

    public Individual createIndividual(OntModel model, String uri) {
        return model.getOntClass(FamixURIs.PREFIX + this.name())
                .createIndividual(this.name() + "/" + uri);
    }
}
