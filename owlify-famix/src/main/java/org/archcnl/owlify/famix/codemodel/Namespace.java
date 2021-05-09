package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.Namespace;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixObjectProperties.namespaceContains;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

public class Namespace {
    public static final Namespace TOP = new Namespace("", null);

    private final String name;
    private final Namespace parent;

    public Namespace(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the parent */
    public Namespace getParent() {
        return parent;
    }

    public void modelIn(FamixOntologyNew ontology, List<String> containedTypeNames) {
        Individual namespace = modelIn(ontology);
        containedTypeNames.forEach(
                name ->
                        namespace.addProperty(
                                ontology.get(namespaceContains),
                                ontology.typeCache().getIndividual(name)));
    }

    private Individual modelIn(FamixOntologyNew ontology) {
        // possible recreation of the individual is safe (covered by a test)
        Individual individual = ontology.createIndividual(Namespace, name);
        individual.addLiteral(ontology.get(hasName), name);

        if (this != TOP) {
            parent.modelIn(ontology).addProperty(ontology.get(namespaceContains), individual);
        }

        return individual;
    }
}
