package org.archcnl.owlify.famix.codemodel;

import java.nio.file.Path;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;

public class SourceFile {
    private final Path path;
    private DefinedType definedType;
    private final Namespace namespace;
    private List<Type> importedTypes;

    public SourceFile(
            Path path, DefinedType definedType, Namespace namespace, List<Type> importedTypes) {
        this.path = path;
        this.definedType = definedType;
        this.namespace = namespace;
        this.importedTypes = importedTypes;
    }

    /** @return the path */
    public Path getPath() {
        return path;
    }

    /** @return the definedType */
    public DefinedType getDefinedType() {
        return definedType;
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
        definedType.firstPass(ontology);
    }

    public void modelSecondPass(FamixOntologyNew ontology) {
        ontology.mainModel()
                .getSoftwareArtifactFileIndividual(
                        path.toString(), ontology.typeCache().getIndividual(definedType.getName()));

        for (String containedName : definedType.getNestedTypeNames()) {
            Individual contained = ontology.typeCache().getIndividual(containedName);

            for (Type imported : importedTypes) {
                contained.addProperty(
                        ontology.codeModel().getObjectProperty(FamixURIs.IMPORTS),
                        imported.getIndividual(ontology));
            }
        }

        definedType.secondPass(ontology);

        namespace.modelIn(ontology, definedType.getNestedTypeNames());
    }
}
