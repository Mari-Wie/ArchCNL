package conformancecheck.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;

import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;
import datatypes.ConstraintViolationsResultSet;
 

public class ConformanceCheckImpl implements IConformanceCheck 
{
	private static final Logger LOG = LogManager.getLogger(ConformanceCheckImpl.class);
	
	private ConformanceCheckOntology ontology;


	@Inject
	public ConformanceCheckImpl() {}

	@Override
	public void createNewConformanceCheck() 
	{
    	LOG.trace("Starting createNewConformanceCheck ...");
		ontology = new ConformanceCheckOntology();
		ontology.newConformanceCheck();

	}

	@Override
	public void validateRule(ArchitectureRule rule, String modelPath, ConstraintViolationsResultSet violations, String outputPath) throws FileNotFoundException 
	{
    	LOG.trace("Starting validateRule ...");
    	ontology.storeArchitectureRule(rule);
		Model codemodel = loadModelFromFile(modelPath);
		
		storeRuleViolationsInOntology(rule, codemodel, violations);
		
		LOG.debug("Adding model to code model");
		codemodel.add(ontology.getModel());
		
		writeModelToFile(codemodel, outputPath);
	}

	private void storeRuleViolationsInOntology(ArchitectureRule rule, Model codeModel, ConstraintViolationsResultSet result) {
		List<ConstraintViolation> violations = result.getViolations();

		for (ConstraintViolation violation : violations) 
		{
			ontology.storeConformanceCheckingResultForRule(codeModel, rule, violation);
		}
	}
	
	private Model loadModelFromFile(String filename) throws FileNotFoundException {
		LOG.debug("Reading model from file: " + filename);
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(new FileReader(filename), "");
		return model;
	}
	
	protected void writeModelToFile(Model codemodel, String outputPath) {
		try 
		{	
			File file = new File(outputPath);			
			LOG.debug("Writing code model to file: " + outputPath);
			codemodel.write(new FileOutputStream(file));
		} 
		catch (FileNotFoundException e) 
		{
			LOG.fatal(e.getMessage());
			e.printStackTrace();
		} 
	}

	@Override
	public Map<String, String> getProvidedNamespaces() {
		HashMap<String, String> res = new HashMap<>();
		res.put("architectureconformance", ConformanceCheckOntologyClassesAndProperties.getOntologyNamespace());
		return res;
	}
}
