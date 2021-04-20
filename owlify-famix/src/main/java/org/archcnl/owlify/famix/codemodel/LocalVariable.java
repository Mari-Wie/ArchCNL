package org.archcnl.owlify.famix.codemodel;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class LocalVariable {
    private final Type type;
    private final String name;

    public LocalVariable(Type type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    /** @return the type */
    public Type getType() {
        return type;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    public void modelIn(FamixOntologyNew ontology, String parentName, Individual method) {
        Individual individual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.LOCAL_VARIABLE)
                        .createIndividual(parentName + "." + name);
        individual.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.HAS_DECLARED_TYPE),
                type.getIndividual(ontology));
        individual.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);
        method.addProperty(
                ontology.codeModel().getObjectProperty(FamixURIs.DEFINES_VARIABLE), individual);
    }
}
