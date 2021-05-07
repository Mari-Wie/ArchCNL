package org.archcnl.owlify.famix.codemodel;

import java.nio.file.Path;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class SourceFile {
    private final Path path;
    private List<DefinedType> definedTypes;
    private final Namespace namespace;
    private List<Type> importedTypes;

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

    /** @return the definedType */
    public List<DefinedType> getDefinedTypes() {
        return definedTypes;
    }

    /** @return the namespace */
    public Namespace getNamespace() {
        return namespace;
    }

    /** @return the importedTypes */
    public List<Type> getImportedTypes() {
        return importedTypes;
    }

    public void modelFirstPass(FamixOntologyNew ontology) {
        definedTypes.forEach(t -> t.firstPass(ontology));
    }

    public void modelSecondPass(FamixOntologyNew ontology) {
        for (DefinedType definedType : definedTypes) {
            ontology.mainModel()
                    .getSoftwareArtifactFileIndividual(
                            path.toString(),
                            ontology.typeCache().getIndividual(definedType.getName()));

            for (String containedName : definedType.getNestedTypeNames()) {
                Individual contained = ontology.typeCache().getIndividual(containedName);

                for (Type imported : importedTypes) {
                    contained.addProperty(
                            ontology.codeModel().getObjectProperty(FamixURIs.IMPORTS),
                            imported.getIndividual(ontology));
                }
            }

            namespace.modelIn(ontology, definedType.getNestedTypeNames());
            definedType.secondPass(ontology);
        }
    }
}
