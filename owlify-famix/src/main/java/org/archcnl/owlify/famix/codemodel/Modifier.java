package org.archcnl.owlify.famix.codemodel;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class Modifier {
    private final String name;

    public Modifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void modelIn(FamixOntologyNew ontology, Individual entity) {
        entity.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_MODIFIER), name);
    }
}
