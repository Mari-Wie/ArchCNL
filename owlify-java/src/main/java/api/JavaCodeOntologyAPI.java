package api;

import startup.OntologyExtractionConfiguration;

public interface JavaCodeOntologyAPI {
	
	public void setConfiguration(OntologyExtractionConfiguration config);
	public void transformCodeToOntology() throws OntologyExtractionConfigurationNotSetException;
	public OntologyExtractionConfiguration getConfiguration();

}
