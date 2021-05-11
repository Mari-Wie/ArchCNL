package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a type. Type definitions are not modeled by this class (use DefinedType instead). Only
 * "declared types" are represented here. For instance, when a local variable "SomeClass variable"
 * is modeled, "SomeClass" would be a "Type", while its definition "class SomeClass {...}" is
 * modeled by a "DefinedType".
 */
public class Type {
    /**
     * Use this value when a type is expected, but none is "present", e.g. as the return type of a
     * constructor. null should not be used in these cases.
     */
    public static final Type UNUSED_VALUE =
            new Type("123456789", "1234", false); // invalid type name

    private final String fullQualifiedName;
    private final String simpleName;
    private final boolean isPrimitive;

    /**
     * Constructor.
     *
     * @param fullQualifiedName The fully qualified name of the type.
     * @param simpleName The simple name (without namespaces etc.) of the type.
     * @param isPrimitive Whether this is a primitive type (e.g. int, float or boolean).
     */
    public Type(String fullQualifiedName, String simpleName, boolean isPrimitive) {
        super();
        this.fullQualifiedName = fullQualifiedName;
        this.simpleName = simpleName;
        this.isPrimitive = isPrimitive;
    }

    /** @return the fully qualified name */
    public String getName() {
        return fullQualifiedName;
    }

    /** @return the simple name */
    public String getSimpleName() {
        return simpleName;
    }

    /** @return whether this is a primitive type */
    public boolean isPrimitive() {
        return isPrimitive;
    }

    /**
     * Returns an OWL individual representing this type in the given ontology. When an individual
     * modeling a type with the same fully qualified type name exists, this individual is returned
     * (stored in the ontology's type cache). Else, a new one is created. Newly created,
     * non-primitive types are always assumed to be classes and to be external. Thus, types defined
     * in the analyzed project must be added to the type cache before this method is called the
     * first time, or else "internal" types will be modeled mistakenly as external ones.
     *
     * @param ontology The ontology in which the code is modeled.
     * @return The corresponding type individual.
     */
    public Individual getIndividual(FamixOntology ontology) {
        if (isPrimitive) {
            return ontology.codeModel().getIndividual(FamixOntology.PREFIX + fullQualifiedName);
        }

        if (!ontology.typeCache().isDefined(fullQualifiedName)) {
            // create a new external type individual

            // TODO: this sets every imported type to be a FamixClass (class or interface), the
            // isInterface is not set
            // this must be fixed, as enums and annotations can also be imported
            Individual typeIndividual = ontology.createIndividual(FamixClass, fullQualifiedName);
            typeIndividual.addLiteral(ontology.get(isExternal), true);
            typeIndividual.addLiteral(ontology.get(hasFullQualifiedName), fullQualifiedName);
            typeIndividual.addLiteral(ontology.get(hasName), simpleName);
            ontology.typeCache().addDefinedType(fullQualifiedName, typeIndividual);
        }

        return ontology.typeCache().getIndividual(fullQualifiedName);
    }
}
