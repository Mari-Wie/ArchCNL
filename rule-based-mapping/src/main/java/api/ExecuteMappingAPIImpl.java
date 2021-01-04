package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of the ExecuteMappingAPI interface. Can be
 * instantiated via the ExecuteMappingAPIFactory. 
 */
class ExecuteMappingAPIImpl implements ExecuteMappingAPI {
	private static final Logger LOG = LogManager.getLogger(ExecuteMappingAPIImpl.class);
	
	private ReasoningConfiguration config;

	public void setReasoningConfiguration(ReasoningConfiguration config) {
		this.config = config;
	}

	public void executeMapping() throws FileNotFoundException {
		OntModel ontModel = readInputs();
		Reasoner reasoner = readMappingRules();
		InfModel infmodel = doMapping(ontModel, reasoner);
		writeOutput(infmodel);
	}

	private void writeOutput(InfModel infmodel) throws FileNotFoundException {
		LOG.debug("Writing output to: " + config.getResultPath());
		infmodel.write(new FileOutputStream(new File(config.getResultPath())));

		LOG.debug("Wrote "+infmodel.listStatements().toList().size()+"statements");
	}

	private InfModel doMapping(OntModel ontModel, Reasoner reasoner) {
		LOG.trace("executing mapping");
		InfModel infmodel = ModelFactory.createInfModel(reasoner, ontModel);
		return infmodel;
	}

	private Reasoner readMappingRules() {
		LOG.debug("reading mapping rules");
		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL(config.getPathToMappingRules()));
		return reasoner;
	}

	private OntModel readInputs() {
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		LOG.debug("Reading code model");
		ontModel.read(config.getPathToData());
		
		for (String path : config.getPathsToConcepts()) {
			LOG.debug("Reading concept file: "+path);
			ontModel.read(path);
		}
		return ontModel;
	}

	public String getReasoningResultPath() {
		return config.getResultPath();
	}
}
