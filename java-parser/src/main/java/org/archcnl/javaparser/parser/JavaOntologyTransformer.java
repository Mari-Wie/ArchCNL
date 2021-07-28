package org.archcnl.javaparser.parser;

import java.nio.file.Path;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntologyTransformer;

/** Transforms the source code of a Java project into an OWL ontology. */
public class JavaOntologyTransformer extends FamixOntologyTransformer {

    @Override
    protected Project extractCodeModel(List<Path> sourcePaths) {
        return new ModelExtractor(sourcePaths).extractCodeModel();
    }
}
