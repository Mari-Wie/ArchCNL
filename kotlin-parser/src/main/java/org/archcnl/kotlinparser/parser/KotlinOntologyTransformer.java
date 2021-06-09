package org.archcnl.kotlinparser.parser;

import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntologyTransformer;

public class KotlinOntologyTransformer extends FamixOntologyTransformer {
    @Override
    protected Project extractCodeModel() {
        return new ModelExtractor(getSourcePaths()).extractCodeModel();
    }
}
