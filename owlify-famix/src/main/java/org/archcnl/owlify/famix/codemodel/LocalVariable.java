package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesVariable;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
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
    private Path path;
    private Optional<Integer> beginning;

    /**
     * Constructor.
     *
     * @param type Declared type of the variable.
     * @param name The name of the variable.
     * @param modifiers A list of variable modifiers (e.g. "static", "private" , etc.)
     * @param path The path of the class this variable is in
     * @param beginning The position of the beginning of the variable
     */
    public LocalVariable(
            Type type,
            String name,
            List<Modifier> modifiers,
            Path path,
            Optional<Integer> beginning) {
        super();
        this.type = type;
        this.name = name;
        this.modifiers = modifiers;
        this.path = path;
        this.beginning = beginning;
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

    /** @return the path */
    public Path getPath() {
        return path;
    }

    /** @return the beginning */
    public Optional<Integer> getBeginning() {
        return beginning;
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

        String location = path.toString();
        if (beginning.isPresent()) {
            location += ", Line: " + beginning.get();
        }
        individual.addLiteral(ontology.get(isLocatedAt), location);

        modifiers.forEach(mod -> mod.modelIn(ontology, individual));

        method.addProperty(ontology.get(definesVariable), individual);
    }
}
