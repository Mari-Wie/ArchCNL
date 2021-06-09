package org.archcnl.javaparser.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntologyTransformer;

/** Transforms the source code of a Java project into an OWL ontology. */
public class JavaOntologyTransformer extends FamixOntologyTransformer {

    @Override
    protected Project extractCodeModel() {
        return new ModelExtractor(getSourcePaths()).extractCodeModel();
    }
}
