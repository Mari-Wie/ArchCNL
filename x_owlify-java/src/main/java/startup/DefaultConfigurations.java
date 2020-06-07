package startup;

public class DefaultConfigurations {

	public static OntologyExtractionConfiguration defaultConfig() {
		String ontologyPath = "./ontology/javacodeontology.owl";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(ontologyPath).withOutput("./result.owl")
				.withSourceCode("C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl_test\\TestProject\\src")
				.withCodeOntologyURI("http://arch-ont.org/ontologies/javacodeontology.owl#");
		return config;
	}

}
