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

class ExecuteMappingAPIImpl implements ExecuteMappingAPI {
	
	private ReasoningConfiguration config;
	private String reasoningResultPath;

	public void setReasoningConfiguration(ReasoningConfiguration config) {
		this.config = config;
		this.reasoningResultPath = "./mapped.owl";
	}

	public void executeMapping() throws FileNotFoundException {
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		System.out.println("reading");
		ontModel.read(config.getPathToData());
		
		if(config.getPathsToConcepts().size() == 0) {
			ontModel.read(config.getPathToConcepts());			
		}
		else {
			for (String path : config.getPathsToConcepts()) {
				System.out.println(path);
				ontModel.read(path);
			}
		}

		System.out.println("reasoner");
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(config.getPathToMappingRules()));
		System.out.println("infmodel");
		InfModel infmodel = ModelFactory.createInfModel(reasoner, ontModel);
		System.out.println("writing to: " + this.reasoningResultPath);
		infmodel.write(new FileOutputStream(new File(this.reasoningResultPath)));

		StmtIterator statements = infmodel.listStatements();
		System.out.println(statements.toList().size());
	}

	public String getReasoningResultPath() {
		return reasoningResultPath;
	}
}
