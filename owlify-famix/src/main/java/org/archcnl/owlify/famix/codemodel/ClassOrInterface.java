package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Inheritance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasSubClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasSuperClass;

import java.nio.file.Path;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties;

/**
 * Models a class or interface type defined in the analyzed project.
 *
 * <p>Represented by the "FamixClass" ontology class.
 */
public class ClassOrInterface extends ClassInterfaceEnum {
    private final boolean isInterface;
    private List<Type> supertypes;

    /**
     * Constructor.
     *
     * @param name Fully qualified name of the type.
     * @param simpleName The simple name of the type.
     * @param nestedTypes List of types which declaration is nested into this one.
     * @param methods List of methods defined for this type.
     * @param fields List of fields defined for this type.
     * @param modifiers List of modifiers for this type.
     * @param annotations List of annotation instances for this type.
     * @param isInterface Whether this is an interface.
     * @param supertypes List of supertypes for this type.
     */
    public ClassOrInterface(
    		Path path,
            String name,
            String simpleName,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations,
            boolean isInterface,
            List<Type> supertypes) {
        super(path, name, simpleName, nestedTypes, methods, fields, modifiers, annotations);
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
    protected Individual createIndividual(FamixOntology ontology) {
        return ontology.createIndividual(FamixClass, getName());
    }

    @Override
    protected void secondPassProcess(FamixOntology ontology, Individual individual) {
        // call super method
        super.secondPassProcess(ontology, individual);

        // add information specific to interfaces and classes
        addSupertypes(ontology, individual);
        individual.addLiteral(ontology.get(FamixDatatypeProperties.isInterface), isInterface);
    }

    private void addSupertypes(FamixOntology ontology, Individual individual) {
        for (Type supertype : supertypes) {
            Individual inheritance =
                    ontology.createIndividual(Inheritance, getName() + "-" + supertype.getName());
            inheritance.addProperty(ontology.get(hasSubClass), individual);
            inheritance.addProperty(ontology.get(hasSuperClass), supertype.getIndividual(ontology));
        }
    }
}
