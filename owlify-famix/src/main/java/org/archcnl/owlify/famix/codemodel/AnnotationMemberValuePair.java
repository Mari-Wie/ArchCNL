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

    public void modelIn(
            FamixOntologyNew ontology, String annotationName, Individual annotationInstance) {
        if (!ontology.annotationAttributeCache().isKnownAttribute(annotationName, name)) {
            Individual attribute =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.ANNOTATION_TYPE_ATTRIBUTE)
                            .createIndividual(annotationName + "-" + name);
            attribute.addLiteral(
                    ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);

            ontology.annotationAttributeCache()
                    .addAnnotationAttribute(annotationName, name, attribute);
        }

        Individual annotationTypeAttribute =
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name);
        Individual annotationInstanceAttribute =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_INSTANCE_ATTRIBUTE)
                        .createIndividual("TODO: name"); // TODO name

        annotationInstanceAttribute.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE),
                annotationTypeAttribute);
        annotationInstance.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE),
                annotationInstanceAttribute);
        annotationInstanceAttribute.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_VALUE), value);
    }
}
