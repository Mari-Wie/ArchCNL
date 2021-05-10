package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationInstanceAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasValue;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstanceAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationTypeAttribute;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models an attribute-value pair present in a particular annotation (instance). For instance, when
 * the annotation (at)Deprecated(since="today") is modeled, there would be a single member value
 * pair ("since", "today").
 *
 * <p>(Mostly) represented by the "AnnotationInstanceAttribute" ontology class (and a few others).
 */
public class AnnotationMemberValuePair {
    private final String name;
    private final String value;

    /**
     * Constructor.
     *
     * @param name Simple name of the attribute.
     * @param value Value of the attribute.
     */
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
     * @param annotationName The fully qualified name of the annotation instance this pair belongs
     *     to.
     * @param parentName A unique name identifying the annotation instance this pair belongs to.
     * @param annotationInstance The annotation instance this pair belongs to.
     */
    public void modelIn(
            FamixOntology ontology,
            String annotationName,
            String parentName,
            Individual annotationInstance) {
        if (!ontology.annotationAttributeCache().isKnownAttribute(annotationName, name)) {
            modelTypeAttribute(ontology, annotationName);
        }

        Individual annotationTypeAttribute =
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name);
        Individual annotationInstanceAttribute =
                ontology.createIndividual(AnnotationInstanceAttribute, parentName + "-" + name);

        annotationInstanceAttribute.addProperty(
                ontology.get(hasAnnotationTypeAttribute), annotationTypeAttribute);
        annotationInstance.addProperty(
                ontology.get(hasAnnotationInstanceAttribute), annotationInstanceAttribute);
        annotationInstanceAttribute.addLiteral(ontology.get(hasValue), value);
    }

    private void modelTypeAttribute(FamixOntology ontology, String annotationName) {
        Individual attribute =
                ontology.createIndividual(AnnotationTypeAttribute, annotationName + "-" + name);
        attribute.addLiteral(ontology.get(hasName), name);
        ontology.annotationAttributeCache().addAnnotationAttribute(annotationName, name, attribute);
    }
}
