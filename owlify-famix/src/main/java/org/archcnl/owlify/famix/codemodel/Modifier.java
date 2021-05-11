package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a single modifier such as access modifiers (e.g. public) or a mutability modifiers (e.g.
 * final).
 */
public class Modifier {
    private final String name;

    /**
     * Constructor.
     *
     * @param name The name/keyword of the modifier, e.g. "private", "static", or "final".
     */
    public Modifier(String name) {
        this.name = name;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /**
     * Models this modifier in the given ontology.
     *
     * @param ontology The famix ontology in which this modifier will be modeled.
     * @param entity The OWL individual of the entity to which this modifier belongs to/applies.
     */
    public void modelIn(FamixOntology ontology, Individual entity) {
        entity.addLiteral(ontology.get(hasModifier), name);
    }
}
