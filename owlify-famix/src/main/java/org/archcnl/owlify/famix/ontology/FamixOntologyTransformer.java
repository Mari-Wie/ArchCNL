package org.archcnl.owlify.famix.ontology;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.famix.codemodel.Project;

public abstract class FamixOntologyTransformer extends AbstractOwlifyComponent {
    @Override
    public Map<String, String> getProvidedNamespaces() {
        Map<String, String> res = new HashMap<>();
        res.put("famix", FamixOntology.PREFIX);
        return res;
    }

    protected abstract Project extractCodeModel();
    
    @Override
    public Model transform() {
        Project analyzedProject = extractCodeModel();

        InputStream famixOntology = getClass().getResourceAsStream("/ontologies/famix.owl");
        InputStream mainOntology = getClass().getResourceAsStream("/ontologies/main.owl");

        FamixOntology ontology = new FamixOntology(famixOntology, mainOntology);

        analyzedProject.modelIn(ontology);

        return ontology.getFinalModel();
    }
}
