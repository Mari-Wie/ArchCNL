package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a user-defined annotation (the declaration). Represented by the "AnnotationType" class in
 * the ontology.
 */
public class Annotation extends DefinedType {

    private List<AnnotationAttribute> attributes;

    /**
     * Constructor.
     *
     * @param name The fully qualified name of the annotation.
     * @param simpleName The simple name of the type.
     * @param annotations List of annotation instances of the declaration.
     * @param modifiers List of modifiers of the declaration.
     * @param attributes List of annotation attributes defined in the declaration.
     */
    public Annotation(
            String path,
            String name,
            String simpleName,
            List<AnnotationInstance> annotations,
            List<Modifier> modifiers,
            List<AnnotationAttribute> attributes) {
        super(path, name, simpleName, annotations, modifiers);
        this.attributes = attributes;
    }

    /** @return the attributes */
    public List<AnnotationAttribute> getAttributes() {
        return attributes;
    }

    @Override
    protected Individual createIndividual(FamixOntology ontology) {
        Individual individual = ontology.createIndividual(AnnotationType, getName());
        attributes.forEach(attr -> attr.modelIn(ontology, getName(), individual));
        return individual;
    }

    @Override
    protected void firstPassPostProcess(FamixOntology ontology) {
        // do nothing, annotations cannot have nested types

    }

    @Override
    protected void secondPassProcess(FamixOntology ontology, Individual individual) {
        // nothing to add here
    }
}
