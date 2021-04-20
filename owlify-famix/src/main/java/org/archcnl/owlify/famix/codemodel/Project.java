package org.archcnl.owlify.famix.codemodel;

import java.util.ArrayList;
import java.util.List;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

public class Project {
    private List<SourceFile> sourceFiles;

    public Project() {
        sourceFiles = new ArrayList<>();
    }

    public void addFile(SourceFile file) {
        sourceFiles.add(file);
    }

    public List<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public void modelIn(FamixOntologyNew ontology) {
        // identify and resolve all user defined types and annotation attributes
        sourceFiles.forEach(file -> file.modelFirstPass(ontology));
        // construct the actual model, "unknown" types are assumed to be external ones
        sourceFiles.forEach(file -> file.modelSecondPass(ontology));
    }
}
