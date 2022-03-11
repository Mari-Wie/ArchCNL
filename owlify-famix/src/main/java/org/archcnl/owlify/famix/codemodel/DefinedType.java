package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a type defined in the analyzed project. These types must be modeled via two passes: In the
 * first pass, all defined types are "memorized". In the second pass, the actual code model/ontology
 * is created. Every "unknown" type was not visited in the first pass. Thus, these types are known
 * to be external once.
 */
public abstract class DefinedType {
    private final String fullyQualifiedName;
    private final String simpleName;
    private List<AnnotationInstance> annotations;
    private List<Modifier> modifiers;
    private final Path path;

    /**
     * Constructor.
     *
     * @param fullyQualifiedName Fully qualified name of the type.
     * @param simpleName The simple name of the type.
     * @param annotations List of annotation instances for the type declaration.
     * @param modifiers List of annotation instances for the type.
     */
    protected DefinedType(
            Path path,
            String fullyQualifiedName,
            String simpleName,
            List<AnnotationInstance> annotations,
            List<Modifier> modifiers) {
        this.fullyQualifiedName = fullyQualifiedName;
        this.simpleName = simpleName;
        this.path = path;
        this.annotations = annotations;
        this.modifiers = modifiers;
    }

    /** @return the fully qualified type name */
    public String getName() {
        return fullyQualifiedName;
    }

    /** @return the simple name */
    public String getSimpleName() {
        return simpleName;
    }

    /** @return the annotation instances */
    public List<AnnotationInstance> getAnnotations() {
        return annotations;
    }

    /** @return the modifiers */
    public List<Modifier> getModifiers() {
        return modifiers;
    }

    /** @return the path */
    public Path getPath() {
        return path;
    }

    /**
     * First pass of the modeling. "Memorizes" this type and its nested types.
     *
     * @param ontology The ontology in which the code model will be created.
     */
    public void firstPass(FamixOntology ontology) {
        // template method: implementing classes determine how individuals are created
        Individual individual = createIndividual(ontology);

        individual.addLiteral(ontology.get(hasName), simpleName);
        individual.addLiteral(ontology.get(hasFullQualifiedName), fullyQualifiedName);
        individual.addLiteral(ontology.get(isExternal), false);
        individual.addLiteral(ontology.get(isLocatedAt), path.toString());

        ontology.typeCache().addDefinedType(fullyQualifiedName, individual);

        // give implementing classes a chance to do some post-processing stuff during the first pass
        firstPassPostProcess(ontology);
    }

    public void secondPass(FamixOntology ontology) {
        Individual individual = ontology.typeCache().getIndividual(fullyQualifiedName);

        annotations.forEach(anno -> anno.modelIn(ontology, fullyQualifiedName, individual));
        modifiers.forEach(mod -> mod.modelIn(ontology, individual));

        // template method: implementing classes can add further information on the type
        secondPassProcess(ontology, individual);
    }

    /** @return A list the fully qualified type names of this type and all nested types. */
    public List<String> getNestedTypeNames() {
        return Arrays.asList(fullyQualifiedName);
    }

    /**
     * Implement this method. It must return the OWL individual by which the type is modeled. DO NOT
     * add further information on the type.
     *
     * @param ontology The ontology in which the type must be created.
     * @return The created OWL individual.
     */
    protected abstract Individual createIndividual(FamixOntology ontology);

    /**
     * Implement this method. Here, some post-processing actions for the first pass can be stated,
     * e.g. invoking the first pass on nested types recursively. DO NOT add further information on
     * the type.
     *
     * @param ontology The ontology in which the type was created.
     */
    protected abstract void firstPassPostProcess(FamixOntology ontology);

    /**
     * Implement this method. Here, some post-processing actions for the second pass can be stated.
     * Additional information on your type must be added here.
     *
     * @param ontology The ontology in which the type was created.
     * @param individual The individual of this type (the one created by {@link
     *     #createIndividual(FamixOntology)}
     */
    protected abstract void secondPassProcess(FamixOntology ontology, Individual individual);
}
