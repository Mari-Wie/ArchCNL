package api;

import parser.OntologyJavaParser;
import startup.OntologyExtractionConfiguration;

public class JavaCodeOntologyAPIImpl implements JavaCodeOntologyAPI {
	
	private OntologyExtractionConfiguration config;

	public void setConfiguration(OntologyExtractionConfiguration config) {
		this.config = config;
	}

	public void transformCodeToOntology() throws OntologyExtractionConfigurationNotSetException {
		
		if(config!=null) 
		{
			OntologyJavaParser parser = new OntologyJavaParser(this.config);
			parser.execute();
		}
		else
		{
			throw new OntologyExtractionConfigurationNotSetException();
		}
		
	}

	public OntologyExtractionConfiguration getConfiguration() {
		return config;
	}

}
