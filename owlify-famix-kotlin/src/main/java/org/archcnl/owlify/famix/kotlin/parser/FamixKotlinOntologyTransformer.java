package org.archcnl.owlify.famix.kotlin.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.core.GeneralSoftwareArtifactOntology;
import org.archcnl.owlify.famix.kotlin.ontology.FamixOntology;

public class FamixKotlinOntologyTransformer extends AbstractOwlifyComponent {
	private static final Logger LOG = LogManager.getLogger(FamixKotlinOntologyTransformer.class);
	private FamixOntology ontology;
	private GeneralSoftwareArtifactOntology mainOntology;

	public FamixKotlinOntologyTransformer(String resultPath) {
		super(resultPath);
		LOG.debug("Reading resource ontologies ...");
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		ontology = new FamixOntology(famixOntologyInputStream);
		InputStream mainOntologyInputStream = getClass().getResourceAsStream("/ontologies/main.owl");
		mainOntology = new GeneralSoftwareArtifactOntology(mainOntologyInputStream);
	}

	@Override
	public Map<String, String> getProvidedNamespaces() {
		HashMap<String, String> res = new HashMap<>();
		res.put("famix", ontology.getOntologyNamespace());
		return res;
	}

	@Override
	public void transform() {
		LOG.trace("Starting kotlin to famix-owl transformation");
		
		// TODO: add transformation
		
		ontology.add(mainOntology.getOntology());
        LOG.debug("Writing code model to the file: " + super.getResultPath());
        ontology.save(super.getResultPath());

	}

}
