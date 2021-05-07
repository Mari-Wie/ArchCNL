package org.archcnl.owlify.famix.codemodel;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class AnnotationAttribute {
    private final String name;
    private final Type type;

    public AnnotationAttribute(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the type */
    public Type getType() {
        return type;
    }

    public void modelIn(FamixOntologyNew ontology, String annotationName, Individual annotation) {
        Individual individual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE_ATTRIBUTE)
                        .createIndividual(annotation.getURI() + "-" + name);
        individual.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);
        individual.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_TYPE),
                type.getIndividual(ontology));

        annotation.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE),
                individual);

        ontology.annotationAttributeCache()
                .addAnnotationAttribute(annotationName, name, individual);
    }
}
