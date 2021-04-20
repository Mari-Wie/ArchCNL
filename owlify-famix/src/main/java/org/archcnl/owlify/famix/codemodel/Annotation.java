package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Annotation extends DefinedType {

    private List<AnnotationAttribute> attributes;

    public Annotation(
            String name,
            List<AnnotationInstance> annotations,
            List<AnnotationAttribute> attributes) {
        super(name, annotations);
        this.attributes = attributes;
    }

    /** @return the attributes */
    public List<AnnotationAttribute> getAttributes() {
        return attributes;
    }

    @Override
    protected Individual createIndividual(FamixOntologyNew ontology) {
        Individual individual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE)
                        .createIndividual(getName());
        attributes.forEach(attr -> attr.modelIn(ontology, getName(), individual));
        return individual;
    }

    @Override
    protected void firstPassPostProcess(FamixOntologyNew ontology) {
        // do nothing, annotations cannot have nested types

    }

    @Override
    protected void secondPassProcess(FamixOntologyNew ontology, Individual individual) {
        // nothing to add here
    }
}
