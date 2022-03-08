package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Enum;

import java.nio.file.Path;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models an enumeration type defined in the analyzed project.
 *
 * <p>Represented by the "Enum" ontology class.
 */
public class Enumeration extends ClassInterfaceEnum {

    /**
     * Constructor.
     *
     * @param name Fully qualified name of the enumeration.
     * @param simpleName The simple name of the type.
     * @param nestedTypes List of nested type definitions.
     * @param methods List of methods defined in this class.
     * @param fields List of fields defined in this class.
     * @param modifiers List of modifiers for this enumeration.
     * @param annotations List of annotation instances for this enumeration.
     */
    public Enumeration(
    		Path path,
            String name,
            String simpleName,
            List<DefinedType> nestedTypes,
            List<Method> methods,
            List<Field> fields,
            List<Modifier> modifiers,
            List<AnnotationInstance> annotations) {
        super(path, name, simpleName, nestedTypes, methods, fields, modifiers, annotations);
    }

    @Override
    protected Individual createIndividual(FamixOntology ontology) {
        return ontology.createIndividual(Enum, getName());
    }
}
