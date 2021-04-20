package org.archcnl.owlify.famix.codemodel;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Type {
    public static final Type UNUSED_VALUE = new Type("123456789", false); // invalid type name

    private final String name;
    private final boolean isPrimitive;

    public Type(String name, boolean isPrimitive) {
        super();
        this.name = name;
        this.isPrimitive = isPrimitive;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the isPrimitive */
    public boolean isPrimitive() {
        return isPrimitive;
    }

    public Individual getIndividual(FamixOntologyNew ontology) {
        if (isPrimitive) {
            return ontology.codeModel().getIndividual(FamixURIs.PREFIX + name);
        }

        if (!ontology.typeCache().isUserDefined(name)) {
            // create a new external type individual

            // TODO: hasName or hasFullQualifiedName

            // TODO: this sets every imported type to be a FamixClass (class or interface), the
            // isInterface is not set
            // this must be fixed, as enums and annotations can also be imported
            Individual typeIndividual =
                    ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual(name);
            typeIndividual.addLiteral(
                    ontology.codeModel().getDatatypeProperty(FamixURIs.IS_EXTERNAL), true);
            ontology.typeCache().addUserDefinedType(name, typeIndividual);
        }

        return ontology.typeCache().getIndividual(name);
    }
}
