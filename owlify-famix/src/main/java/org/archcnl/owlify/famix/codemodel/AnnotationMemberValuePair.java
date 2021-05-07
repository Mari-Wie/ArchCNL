package org.archcnl.owlify.famix.codemodel;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class AnnotationMemberValuePair {
    private final String name;
    private final String value;

    public AnnotationMemberValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the value */
    public String getValue() {
        return value;
    }

    /**
     * Models this annotation member-value-pair in the given ontology.
     *
     * @param ontology The ontology in which this pair will be modeled.
     * @param annotationName The fully qualified name of the annotation this pair belongs to.
     * @param annotationInstance
     */
    public void modelIn(
            FamixOntologyNew ontology, String annotationName, Individual annotationInstance) {
        if (!ontology.annotationAttributeCache().isKnownAttribute(annotationName, name)) {
            modelTypeAttribute(ontology, annotationName);
        }

        Individual annotationTypeAttribute =
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name);
        Individual annotationInstanceAttribute =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_INSTANCE_ATTRIBUTE)
                        .createIndividual(annotationInstance.getURI() + "-" + name);

        annotationInstanceAttribute.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE),
                annotationTypeAttribute);
        annotationInstance.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE),
                annotationInstanceAttribute);
        annotationInstanceAttribute.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_VALUE), value);
    }

    private void modelTypeAttribute(FamixOntologyNew ontology, String annotationName) {
        Individual attribute =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE_ATTRIBUTE)
                        .createIndividual(
                                annotationName
                                        + "-"
                                        + name); // TODO name creation is a code duplicate
        attribute.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);

        ontology.annotationAttributeCache().addAnnotationAttribute(annotationName, name, attribute);
    }
}
