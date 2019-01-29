package api;

import core.OwlifyComponent;
import parser.OntologyJavaParser;
import startup.OntologyExtractionConfiguration;

public class JavaCodeOntologyAPIImpl implements OwlifyComponent {
	
	private OntologyExtractionConfiguration config;
	private final String PATH_TO_RESULT = "./java_result.owl";

	public void setConfiguration(OntologyExtractionConfiguration config) {
		this.config = config;
	}
	
	public void transform() {
		
		if(config!=null) 
		{
			OntologyJavaParser parser = new OntologyJavaParser(this.config);
			parser.execute();
		}
	}

	public OntologyExtractionConfiguration getConfiguration() {
		return config;
	}

	public void setSource(String path) {
		String ontologyPath = "./ontology/javacodeontology.owl";

		this.config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(ontologyPath).withOutput("./java_result.owl")
				.withSourceCode(path)
				.withCodeOntologyURI("http://arch-ont.org/ontologies/javacodeontology.owl#");
	}

	public String getResultFile() {
		return PATH_TO_RESULT;
	}

}
