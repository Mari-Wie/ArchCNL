package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesVariable;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;

import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;

/**
 * Models a local variable.
 *
 * <p>Represented by the "LocalVariable" ontology class.
 */
public class LocalVariable {
    private final Type type;
    private final String name;
    private List<Modifier> modifiers;
    private String location;

    /**
     * Constructor.
     *
     * @param type Declared type of the variable.
     * @param name The name of the variable.
     * @param modifiers A list of variable modifers (e.g. "static", "private" , etc.)
     */
    public LocalVariable(String location, Type type, String name, List<Modifier> modifiers) {
        super();
        this.type = type;
        this.name = name;
        this.modifiers = modifiers;
        this.location = location;
    }

    /** @return the declared type */
    public Type getType() {
        return type;
    }

    /** @return the name */
    public String getName() {
        return name;
    }

    /** @return the list of modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    /**
     * Models this variable in the given ontology.
     *
     * @param ontology The famix ontology in which this variable will be modeled.
     * @param parentName A unique name identifying the method in which this variable is defined.
     * @param method The OWL individual representing the method in which this variable is defined.
     */
    public void modelIn(FamixOntology ontology, String parentName, Individual method) {
        String uri = parentName + "." + name;
        Individual individual = ontology.createIndividual(FamixClasses.LocalVariable, uri);
        individual.addProperty(ontology.get(hasDeclaredType), type.getIndividual(ontology));
        individual.addLiteral(ontology.get(hasName), name);
        individual.addLiteral(ontology.get(isLocatedAt), location);

        modifiers.forEach(mod -> mod.modelIn(ontology, individual));

        method.addProperty(ontology.get(definesVariable), individual);
    }
}
