package org.archcnl.owlify.famix.parser;

import java.io.InputStream;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;

public class JavaOntologyTransformer extends AbstractOwlifyComponent {

    @Override
    public Map<String, String> getProvidedNamespaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Model transform() {
        ModelExtractor extractor = new ModelExtractor(getSourcePaths());

        Project analyzedProject = extractor.extractCodeModel();

        InputStream famixOntology = getClass().getResourceAsStream("/ontologies/famix.owl");
        InputStream mainOntology = getClass().getResourceAsStream("/ontologies/main.owl");

        FamixOntologyNew ontology = new FamixOntologyNew(famixOntology, mainOntology);

        analyzedProject.modelIn(ontology);

        return ontology.getFinalModel();
    }
}
