package org.archcnl.kotlinparser.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.jena.rdf.model.Model;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.ontology.FamixOntology;

public class KotlinOntologyTransformer extends AbstractOwlifyComponent {

    @Override
    public Map<String, String> getProvidedNamespaces() {
        Map<String, String> res = new HashMap<>();
        res.put("famix", FamixOntology.PREFIX);
        return res;
    }

	@Override
	public Model transform() {
		ModelExtractor extractor = new ModelExtractor(getSourcePaths());

        Project analyzedProject = extractor.extractCodeModel();

        InputStream famixOntology = getClass().getResourceAsStream("/ontologies/famix.owl");
        InputStream mainOntology = getClass().getResourceAsStream("/ontologies/main.owl");

        FamixOntology ontology = new FamixOntology(famixOntology, mainOntology);

        analyzedProject.modelIn(ontology);

        return ontology.getFinalModel();
	}
}
