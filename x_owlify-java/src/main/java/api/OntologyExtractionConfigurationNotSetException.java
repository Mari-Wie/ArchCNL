package api;

import startup.OntologyExtractionConfiguration;

public class OntologyExtractionConfigurationNotSetException extends Exception {

	
	@Override
	public String getMessage() {

		return "A configuration was not set for the parser. Please use setConfig(OntologyExtractionConfiguration config) in order to specify a configuration.";	
	}
	
}
