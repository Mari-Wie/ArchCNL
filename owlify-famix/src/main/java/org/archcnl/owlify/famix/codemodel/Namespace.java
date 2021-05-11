package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Namespace;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.namespaceContains;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a namespace.
 *
 * <p>Represented by the "Namespace" ontology class.
 */
public class Namespace {
    /** Artificial "topmost" namespace, in which every other namespace is contained. */
    public static final Namespace TOP = new Namespace("", null);

    private final String name;
    private final Namespace parent;

    /**
     * Constructor.
     *
     * @param name The fully qualified name of this namespace.
     * @param parent The parent namespace, i.e. the one in which this one is contained.
     */
    public Namespace(String name, Namespace parent) {
        this.name = name;
        this.parent = parent;
    }

    /** @return the fully qualified name */
    public String getName() {
        return name;
    }

    /** @return the parent */
    public Namespace getParent() {
        return parent;
    }

    /**
     * Models this namespace and all parent namespaces. Duplicate modeling of parent namespaces has
     * no side effects.
     *
     * @param ontology The famix ontology in which the namespaces will be modeled.
     * @param containedTypeNames List of type names defined in this namespace. This namespace is set
     *     to contain these types.
     */
    public void modelIn(FamixOntology ontology, List<String> containedTypeNames) {
        Individual namespace = modelIn(ontology);
        containedTypeNames.forEach(
                name ->
                        namespace.addProperty(
                                ontology.get(namespaceContains),
                                ontology.typeCache().getIndividual(name)));
    }

    private Individual modelIn(FamixOntology ontology) {
        // possible recreation of the individual is safe (covered by a test)
        Individual individual = ontology.createIndividual(Namespace, name);
        individual.addLiteral(ontology.get(hasName), name);

        if (this != TOP) {
            parent.modelIn(ontology).addProperty(ontology.get(namespaceContains), individual);
        }

        return individual;
    }
}
