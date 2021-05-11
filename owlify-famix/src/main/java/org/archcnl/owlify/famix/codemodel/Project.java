package org.archcnl.owlify.famix.codemodel;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.ontology.FamixOntology;

/**
 * Root of the code model aggregate. Add the parsed SourceFiles, then trigger the modeling/OWL
 * transformation process here.
 *
 * <p>Represents the entire analyzed project (i.e. all code/source files).
 */
public class Project {
    private List<SourceFile> sourceFiles;

    /** Constructor. */
    public Project() {
        sourceFiles = new ArrayList<>();
    }

    /**
     * Adds the given SourceFile to the project. Do not add duplicates.
     *
     * @param file The SourceFile to add.
     */
    public void addFile(SourceFile file) {
        sourceFiles.add(file);
    }

    /** @return a list of all source files which have been added so far. */
    public List<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    /**
     * Transforms the code model rooted in this object to OWL.
     *
     * @param ontology The famix ontology in which the project will be modeled.
     */
    public void modelIn(FamixOntology ontology) {
        // identify and resolve all user defined types and annotation attributes
        sourceFiles.forEach(file -> file.modelFirstPass(ontology));
        // construct the actual model, "unknown" types are assumed to be external ones
        sourceFiles.forEach(file -> file.modelSecondPass(ontology));
    }
}
