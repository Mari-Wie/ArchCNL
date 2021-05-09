package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.AnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixObjectProperties.hasAnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixObjectProperties.hasDeclaredType;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

/**
 * Models a single annotation attribute defined for an annotation. Represented by the
 * "AnnotationTypeAttribute" ontology class.
 */
public class AnnotationAttribute {
    private final String name;
    private final Type type;

    /**
     * Constructor.
     *
     * @param name (Simple) name of the attribute.
     * @param type The declared type of the attribute.
     */
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

    /**
     * Models this annotation attribute in the given ontology.
     *
     * @param ontology The famix ontology in which this will be modeled.
     * @param annotationName The fully qualified name of the annotation to which this attribute
     *     belongs.
     * @param annotation The OWL individual of the annotation to which this attribute belongs.
     */
    public void modelIn(FamixOntologyNew ontology, String annotationName, Individual annotation) {
        Individual individual =
                ontology.createIndividual(AnnotationTypeAttribute, annotationName + "/" + name);

        individual.addLiteral(ontology.get(hasName), name);
        individual.addProperty(ontology.get(hasDeclaredType), type.getIndividual(ontology));

        annotation.addProperty(ontology.get(hasAnnotationTypeAttribute), individual);

        ontology.annotationAttributeCache()
                .addAnnotationAttribute(annotationName, name, individual);
    }
}
