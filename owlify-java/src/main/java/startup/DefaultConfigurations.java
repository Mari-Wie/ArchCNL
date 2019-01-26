package startup;

public class DefaultConfigurations {
	
	public static OntologyExtractionConfiguration defaultConfig() {
		String ontologyPath = "C:\\Users\\sandr\\Documents\\repositories\\Promotion\\DL_formalizations\\ontologies\\ontologies_unified\\";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						ontologyPath+"javacodeontology.owl")
				.withOutput("./result.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl\\ExamplePlugin\\src\\")
				.withCodeOntologyURI("http://arch-ont.org/ontologies/javacodeontology.owl#");
		return config;
	}

}
