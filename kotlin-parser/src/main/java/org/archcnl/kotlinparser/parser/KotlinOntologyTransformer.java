package org.archcnl.kotlinparser.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.kotlinparser.ontology.FamixOntology;
import org.archcnl.owlify.core.AbstractOwlifyComponent;
import org.archcnl.owlify.core.GeneralSoftwareArtifactOntology;

public class KotlinOntologyTransformer extends AbstractOwlifyComponent {
	private static final Logger LOG = LogManager.getLogger(KotlinOntologyTransformer.class);
	private FamixOntology ontology;
	private GeneralSoftwareArtifactOntology mainOntology;

	public KotlinOntologyTransformer(String resultPath) {
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
		
		for (Path sourcePath : super.getSourcePaths()) {
			var fileVisitor = new FileVisitor();
			try {
				Files.walkFileTree(sourcePath, fileVisitor);
			} catch (IOException e) {
				LOG.error("FileVisitor produced an error: ", e);
				e.printStackTrace();
			}
		}
		
		ontology.add(mainOntology.getOntology());
        LOG.debug("Writing code model to the file: " + super.getResultPath());
        ontology.save(super.getResultPath());

	}

}
