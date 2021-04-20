package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class ClassOrInterface extends ClassInterfaceEnum {
    private final boolean isInterface;
    private List<Type> supertypes;

    public ClassOrInterface(
            String name,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations,
            boolean isInterface,
            List<Type> supertypes) {
        super(name, nestedTypes, methods, fields, modifiers, annotations);
        this.isInterface = isInterface;
        this.supertypes = supertypes;
    }

    /** @return the isInterface */
    public boolean isInterface() {
        return isInterface;
    }

    /** @return the supertypes */
    public List<Type> getSupertypes() {
        return supertypes;
    }

    @Override
    protected Individual createIndividual(FamixOntologyNew ontology) {
        return ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual(getName());
    }

    @Override
    protected void secondPassProcess(FamixOntologyNew ontology, Individual individual) {
        // call super method
        super.secondPassProcess(ontology, individual);

        // add information specific to interfaces and classes
        addSupertypes(ontology, individual);
        individual.addLiteral(
                ontology.codeModel().getDatatypeProperty(FamixURIs.IS_INTERFACE), isInterface);
    }

    private void addSupertypes(FamixOntologyNew ontology, Individual individual) {
        for (Type supertype : supertypes) {
            Individual inheritance =
                    ontology.codeModel()
                            .getOntClass(FamixURIs.INHERITANCE)
                            .createIndividual(getName() + "-" + supertype.getName());
            inheritance.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_SUBCLASS), individual);
            inheritance.addProperty(
                    ontology.codeModel().getObjectProperty(FamixURIs.HAS_SUPERCLASS),
                    supertype.getIndividual(ontology));
        }
    }
}
