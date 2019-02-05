package api;

import core.AbstractOwlifyComponent;
import core.OwlifyComponent;
import parser.OntologyJavaParser;
import startup.OntologyExtractionConfiguration;

public class JavaCodeOntologyAPIImpl extends AbstractOwlifyComponent {
	
	private OntologyExtractionConfiguration config;
	
	public JavaCodeOntologyAPIImpl() {
		super("./java_result.owl");
	}

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
				.withCodeOntology(ontologyPath).withOutput(super.getResultPath())
				.withSourceCode(path)
				.withCodeOntologyURI("http://arch-ont.org/ontologies/javacodeontology.owl#");
	}


}
