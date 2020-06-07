package startup;

public class Configurations {
	
	//Surface Configurations
	
	public static OntologyExtractionConfiguration teammates_package_common() {
		String base = "C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\package_rules\\Common_Should_Not_Have_Access_to_any_Packages\\surface\\";
		String ontologyPath = "C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\metrics_working_version\\teammates\\ontologies_unified\\";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						ontologyPath+"javacodeontology.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}
	
	public static OntologyExtractionConfiguration simple_mapping_teammates() {
		
		String base ="C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\metrics_working_version\\teammates\\ontologies_unified\\";
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						base+"javacodeontology.owl")
				.withArchitectureCodeMappingOntology(
						base+"mapping.owl")
				.withArchitectureConceptsOntology(
						base+"architecture.owl")
				.withOutput(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\metrics_working_version\\teammates\\result.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\teammates")
				.withArchitectureCodeMappingURI(
						"http://www.semanticweb.org/sandr/ontologies/2018/1/simple_mapping.owl#")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		
		return config;
	}
	
	public static OntologyExtractionConfiguration test_simple_mapping() {
		String base ="C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\test\\surface\\simplearchitecturemetamodel\\";
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/sandr/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withArchitectureCodeMappingOntology(
						base+"mapping.owl")
				.withArchitectureConceptsOntology(
						base+"architecture.owl")
				.withOutput(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\test\\surface\\test.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\cocome-plain-java\\src\\org\\cocome\\tradingsystem")
				.withArchitectureCodeMappingURI(
						"http://www.semanticweb.org/sandr/ontologies/2018/1/simple_mapping.owl#")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		
		return config;
	}
	
	public static OntologyExtractionConfiguration test_surface() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/sandr/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\test\\surface\\test.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\cocome-plain-java\\src\\org\\cocome\\tradingsystem\\cashdeskline")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}
	
	public static OntologyExtractionConfiguration teammates_surface_metric_measurement() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\metrics_working_version\\teammates\\ontologies_unified\\javacodeontology.owl")
				.withOutput(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\metrics_working_version\\teammates\\result.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\teammates\\")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_surface_domain_model() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/sandr/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(
						"C:\\Users\\sandr\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\domain_model\\test.owl")
				.withSourceCode(
						"C:\\Users\\sandr\\Documents\\Promotion\\casestudies\\teammates\\")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}
	
	
	
	//DELL Laptop Configurations
	
	public static OntologyExtractionConfiguration teammates() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\architecture_design_principles\\test_teammates.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates2() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withArchitectureCodeMappingOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/teammates_formalization/ecsa2018/logic_db_rules_concepts/teammates_architecture_code_mapping.owl")
				.withArchitectureConceptsOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/teammates_formalization/ecsa2018/logic_db_rules_concepts/teammates_architecture_concepts.owl")
				.withOutput(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\result_withjavacodeontology\\logic_db_rules\\result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withArchitectureCodeMappingURI(
						"http://www.semanticweb.org/sandra/ontologies/2018/1/architecture_code_mapping_logic_db#")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_logic_component_rules() {
		String base = "C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/teammates_formalization/ecsa2018/logic_component_rules/logiccore/logic_core_should_not_access_logic_api/";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withArchitectureCodeMappingOntology("file:/" + base + "logic_core_concepts_mapping.owl")
				.withArchitectureConceptsOntology("file:/" + base + "logic_core_concepts.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withArchitectureCodeMappingURI(
						"http://www.semanticweb.org/sandra/ontologies/2018/1/teammates_logic_component_code_mapping#")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_logic_core_should_not_access_logic_api() {
		String base = "C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/teammates_formalization/ecsa2018/logic_component_rules/logiccore/logic_core_should_not_access_logic_api/";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_logic_core_cannot_access_logic_backdoor() {
		String base = "C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\logic_component_rules\\logiccore\\logic_core_cannot_access_logic_backdoor\\";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_logic_classes_can_only_access_storage_api() {
		String base = "C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\logic_component_rules\\logiccore\\logic_classes_can_only_access_storage_api\\";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration teammates_package_common_no_dependencies() {
		String base = "C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\Promotion\\DL_formalizations\\teammates_formalization\\ecsa2018\\package_rules\\Common_Should_Not_Have_Access_to_any_Packages\\";

		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"file:/C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/javacodeontology/javacodeontology.owl")
				.withOutput(base + "result.owl")
				.withSourceCode(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\casestudies\\teammates\\src\\main\\java\\teammates")
				.withCodeOntologyURI("http://www.semanticweb.org/sandra/ontologies/2018/1/javacodeontology#");
		return config;
	}

	public static OntologyExtractionConfiguration petclinic() {
		OntologyExtractionConfiguration config = OntologyExtractionConfiguration.newOntologyExtractionConfiguration()
				.withCodeOntology(
						"C:\\Users\\Sandra\\Documents\\Promotion\\repositories\\jdt2famix\\ontologies\\famix_model_ontology.owl")
				.withOutput("C:\\Users\\Sandra\\Documents\\Promotion\\casestudies\\spring-petclinic\\result.owl")
				.withSourceCode("C:\\Users\\Sandra\\Documents\\Promotion\\casestudies\\spring-petclinic")
				.withArchitectureCodeMappingURI(
						"http://www.semanticweb.org/sandra/ontologies/2017/3/famix_model_ontology#");
		return config;
	}

}
