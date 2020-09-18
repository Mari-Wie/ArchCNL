package startup;

public class OntologyExtractionConfiguration {
	
	private String pathToSourceCode;
	private String pathToOntology;
	private String pathToOutput;
	private String modelURI;
	private String architectureCodeMappingOntology;
	private String architectureConceptsOntology;
	private String codeOntologyURI;
	
	public static OntologyExtractionConfiguration newOntologyExtractionConfiguration() {
		return new OntologyExtractionConfiguration();
	}
	
	public OntologyExtractionConfiguration withSourceCode(String path) {
		this.pathToSourceCode = path;
		return this;
	}
	
	public OntologyExtractionConfiguration withCodeOntology(String path) {
		this.pathToOntology = path;
		return this;
	}
	
	public OntologyExtractionConfiguration withOutput(String path) {
		this.pathToOutput = path;
		return this;
	}
	
	public OntologyExtractionConfiguration withArchitectureCodeMappingURI(String modelURI) {
		this.modelURI = modelURI;
		return this;
	}

	public String getPathToSourceCode() {
		return pathToSourceCode;
	}

	public String getPathToCodeOntology() {
		return pathToOntology;
	}

	public String getPathToOutput() {
		return pathToOutput;
	}

	public String getModelURI() {
		return modelURI;
	}

	public OntologyExtractionConfiguration withArchitectureCodeMappingOntology(String architectureCodeMapping) {
		this.architectureCodeMappingOntology = architectureCodeMapping;
		return this;
	}
	
	public OntologyExtractionConfiguration withArchitectureConceptsOntology(String architectureConcepts) {
		this.architectureConceptsOntology = architectureConcepts;
		return this;
	}

	public String getWithArchitectureCodeMappingOntology() {
		return architectureCodeMappingOntology;
	}

	public String getWithArchitectureConceptsOntology() {
		return architectureConceptsOntology;
	}
	
	public boolean architectureCodeMappingOntologyIsConfigured() {
		return this.architectureCodeMappingOntology!=null;
	}
	
	public boolean architectureConceptsOntologyIsConfigured() {
		return this.architectureConceptsOntology!=null;
	}
	
	public OntologyExtractionConfiguration withCodeOntologyURI(String codeURI) {
		this.codeOntologyURI = codeURI;
		return this;
	}
	
	public String getCodeOntologyURI() {
		return codeOntologyURI;
	}

}
