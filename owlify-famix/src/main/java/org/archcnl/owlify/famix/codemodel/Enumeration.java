package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Enumeration extends ClassInterfaceEnum {

    public Enumeration(
            String name,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations) {
        super(name, nestedTypes, methods, fields, modifiers, annotations);
    }

    @Override
    protected Individual createIndividual(FamixOntologyNew ontology) {
        return ontology.codeModel().getOntClass(FamixURIs.ENUMERATION).createIndividual(getName());
    }
}
