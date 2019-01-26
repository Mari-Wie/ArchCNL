//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
//import org.apache.jena.ontology.OntModel;
//import org.apache.jena.rdf.model.InfModel;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.reasoner.Reasoner;
//import org.semanticweb.owlapi.model.IRI;
//import org.semanticweb.owlapi.model.OWLOntologyCreationException;
//import org.semanticweb.owlapi.model.OWLOntologyManager;
//import org.semanticweb.owlapi.model.OWLOntologyStorageException;
//import org.semanticweb.owlapi.util.SimpleIRIMapper;
//
//import openllet.jena.PelletReasonerFactory;
//import openllet.owlapi.OWL;
//
//public class Test {
//
//	public static void main(String[] args)
//			throws OWLOntologyCreationException, FileNotFoundException, OWLOntologyStorageException {
//		
//		String base = "C:/Users/Sandra/Documents/Promotion/repositories/Promotion/DL_formalizations/teammates_formalization/ecsa2018/logic_component_rules/logiccore/";
//		
//
//		final String file_famix_ontology_model_teammates = base+"result.owl";
//		final String teammates_output = base+"inferred_test.owl";
//
//		final String file_teammates_architecture_code_mapping = base+"logic_core_concepts_mapping.owl";
//
////		final OWLOntologyManager manager = OWL._manager;
////		manager.getIRIMappers()
////				.add(new SimpleIRIMapper(
////						IRI.create("http://semanticweb.org/sandra/ontologies/2017/8/teammates_architecture_concepts"),
////						IRI.create(new File(file_teammates_architecture_concepts))));
//
//		// underlying openllet graph
//		OntModel model = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//		OntModel modelMapping = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
//
//		model.read(file_famix_ontology_model_teammates);
//		modelMapping.read(file_teammates_architecture_code_mapping);
////		model.prepare();
////		modelMapping.prepare();
//
//		System.out.println("reasoner");
//		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
//		System.out.println("infmodel");
//		InfModel inf = ModelFactory.createInfModel(reasoner, modelMapping, model);
//		System.out.println("write");
//		inf.write(new FileOutputStream(new File(teammates_output)));
//
//	}
//
//}
