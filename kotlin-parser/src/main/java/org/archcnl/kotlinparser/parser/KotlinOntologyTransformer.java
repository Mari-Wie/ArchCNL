package org.archcnl.kotlinparser.parser;

import java.nio.file.Path;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntologyTransformer;

public class KotlinOntologyTransformer extends FamixOntologyTransformer {
    @Override
    protected Project extractCodeModel(List<Path> sourcePaths) {
        return new ModelExtractor(sourcePaths).extractCodeModel();
    }
}
