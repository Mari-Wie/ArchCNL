package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

/**
 * Implementation of the ExecuteMappingAPI interface. Can be
 * instantiated via the ExecuteMappingAPIFactory. 
 */
class ExecuteMappingAPIImpl implements ExecuteMappingAPI {
	
	private ReasoningConfiguration config;
	private String reasoningResultPath;

	public void setReasoningConfiguration(ReasoningConfiguration config, String outputFilePath) {
		this.config = config;
		this.reasoningResultPath = outputFilePath;
	}

	public void executeMapping() throws FileNotFoundException {
		OntModel ontModel = readInputs();
		Reasoner reasoner = readMappingRules();
		InfModel infmodel = doMapping(ontModel, reasoner);
		writeOutput(infmodel);
	}

	private void writeOutput(InfModel infmodel) throws FileNotFoundException {
		System.out.println("writing to: " + this.reasoningResultPath);
		infmodel.write(new FileOutputStream(new File(this.reasoningResultPath)));

		StmtIterator statements = infmodel.listStatements();
		System.out.println(statements.toList().size());
	}

	private InfModel doMapping(OntModel ontModel, Reasoner reasoner) {
		System.out.println("infmodel");
		InfModel infmodel = ModelFactory.createInfModel(reasoner, ontModel);
		return infmodel;
	}

	private Reasoner readMappingRules() {
		System.out.println("reasoner");
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(config.getPathToMappingRules()));
		return reasoner;
	}

	private OntModel readInputs() {
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		System.out.println("reading");
		ontModel.read(config.getPathToData());
		
		for (String path : config.getPathsToConcepts()) {
			System.out.println(path);
			ontModel.read(path);
		}
		return ontModel;
	}

	public String getReasoningResultPath() {
		return reasoningResultPath;
	}
}
