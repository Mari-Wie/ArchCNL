package org.archcnl.owlify.famix.codemodel;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

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
                                ontology.codeModel()
                                        .getObjectProperty(FamixURIs.NAMESPACE_CONTAINS),
                                ontology.typeCache().getIndividual(name)));
    }

    private Individual modelIn(FamixOntologyNew ontology) {
        OntClass namespaceClass = ontology.codeModel().getOntClass(FamixURIs.NAMESPACE);
        // possible recreation of the individual is safe (covered by a test)
        Individual individual = namespaceClass.createIndividual("NAMESPACE/" + name);
        individual.addLiteral(ontology.codeModel().getDatatypeProperty(FamixURIs.HAS_NAME), name);

        if (this != Namespace.TOP) {
            parent.modelIn(ontology)
                    .addProperty(
                            ontology.codeModel().getObjectProperty(FamixURIs.NAMESPACE_CONTAINS),
                            individual);
        }

        return individual;
    }
}
