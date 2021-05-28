package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.imports;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Models a single source file in the analyzed project.
 *
 * <p>Represented in the "main ontology" by the "SoftwareArtifactFile" ontology class.
 */
public class SourceFile {
    private final Path path;
    private List<DefinedType> definedTypes;
    private final Namespace namespace;
    private List<Type> importedTypes;

    /**
     * Constructor.
     *
     * @param path The path of the represented file.
     * @param definedTypes List of top-level types defined in the file. Add nested types to their
     *     "parent" type instead.
     * @param namespace The namespace which applies to this file.
     * @param importedTypes The types imported/included in this file.
     */
    public SourceFile(
            Path path,
            List<DefinedType> definedTypes,
            Namespace namespace,
            List<Type> importedTypes) {
        this.path = path;
        this.definedTypes = definedTypes;
        this.namespace = namespace;
        this.importedTypes = importedTypes;
    }

    /** @return the path */
    public Path getPath() {
        return path;
    }

    /** @return the defined top-level types */
    public List<DefinedType> getDefinedTypes() {
        return definedTypes;
    }

    /** @return the namespace */
    public Namespace getNamespace() {
        return namespace;
    }

    /** @return the imported types */
    public List<Type> getImportedTypes() {
        return importedTypes;
    }

    /**
     * First pass of the modeling/OWL transformation. Identifies all types defined in the project.
     *
     * @param ontology The ontology in which the modeling happens.
     */
    public void modelFirstPass(FamixOntology ontology) {
        definedTypes.forEach(t -> t.firstPass(ontology));
    }

    /**
     * Second pass of the modeling/OWL transformation. Here, the actual modeling happens.
     *
     * @param ontology The ontology in which the code will be modeled.
     */
    public void modelSecondPass(FamixOntology ontology) {
        List<Individual> topLevelIndividuals = definedTypes
                .stream()
                .map(t -> ontology.typeCache().getIndividual(t.getName()))
                .collect(Collectors.toList());
        ontology.mainModel().modelSoftwareArtifactFile(path.toString(), topLevelIndividuals);
        
        for (DefinedType definedType : definedTypes) {
            for (String containedName : definedType.getNestedTypeNames()) {
                Individual contained = ontology.typeCache().getIndividual(containedName);

                for (Type imported : importedTypes) {
                    contained.addProperty(ontology.get(imports), imported.getIndividual(ontology));
                }
            }

            namespace.modelIn(ontology, definedType.getNestedTypeNames());
            definedType.secondPass(ontology);
        }
    }
}
