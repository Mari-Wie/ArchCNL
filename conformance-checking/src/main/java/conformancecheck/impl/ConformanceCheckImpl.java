package conformancecheck.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.google.inject.Inject;

import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ConstraintViolation;
import datatypes.ConstraintViolationsResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

public class ConformanceCheckImpl implements IConformanceCheck 
{
	private static final Logger LOG = LogManager.getLogger(ConformanceCheckImpl.class);
	
	private ConformanceCheckOntology ontology;


	@Inject
	public ConformanceCheckImpl() {}

	@Override
	public void createNewConformanceCheck() 
	{
    	LOG.info("Starting createNewConformanceCheck ...");
		ontology = new ConformanceCheckOntology();
		ontology.newConformanceCheck();

	}

	@Override
	public void validateRule(ArchitectureRule rule, String modelPath, ConstraintViolationsResultSet violations, String outputPath) 
	{
    	LOG.info("Starting validateRule ...");
    	ontology.storeArchitectureRule(rule);
		Model codemodel = loadModelFromFile(modelPath);
		
		storeRuleViolationsInOntology(rule, codemodel, violations);
		
		LOG.info("add model to code model");
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
	
	// TODO: better approach?
	// the following methods are protected so that they can be used as a seam during testing
	protected Model loadModelFromFile(String filename) {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(filename);
		return model;
	}
	
	protected void writeModelToFile(Model codemodel, String outputPath) {
		try 
		{	
			File file = new File(outputPath);			
			LOG.info("write to code model");
			codemodel.write(new FileOutputStream(file));
		} 
		catch (FileNotFoundException e) 
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		} 
	}
}
