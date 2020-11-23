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

import api.ConstraintViolation;
import api.ConstraintViolationsResultSet;
import api.StardogDatabaseInterface;
import api.StardogICVAPI;
import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

public class ConformanceCheckImpl implements IConformanceCheck 
{
	private static final Logger LOG = LogManager.getLogger(ConformanceCheckImpl.class);
	
	private ConformanceCheckOntology ontology;

	private String resultPath;

	@Inject
	public ConformanceCheckImpl() 
	{
		String dir = "./conformance_checks/";
		new File(dir).mkdirs();
		this.resultPath = dir + "check.owl";
	}

	@Override
	public void createNewConformanceCheck() 
	{
    	LOG.info("Starting createNewConformanceCheck ...");
		ontology = new ConformanceCheckOntology();
		ontology.newConformanceCheck();

	}

	@Override
	public String validateRule(ArchitectureRule rule, StardogDatabaseInterface db, String context, String modelPath, StardogICVAPI icvAPI) 
	{
		// TODO: resolve dependency on StardogConnector
    	LOG.info("Starting validateRule ...");
    	ontology.storeArchitectureRule(rule);
		String path = ArchitectureRules.getInstance().getPathOfConstraintForRule(rule); // TODO: remove dependency on the singleton
		
		try 
		{
			String constraint = icvAPI.addIntegrityConstraint(path, db.getServer(), db.getDatabaseName());
			rule.setStardogConstraint(constraint);
		}
		catch (FileNotFoundException e) 
		{
			LOG.error(e.getMessage()+ " : " + path);
		}

		icvAPI.explainViolationsForContext(db.getServer(), db.getDatabaseName(), context);
		
		Model codemodel = loadModelFromFile(modelPath);
    	
		storeRuleViolationsInOntology(rule, codemodel, icvAPI.getResult());
		
		LOG.info("add model to code model");
		codemodel.add(ontology.getModel());
		
		writeModelToFile(codemodel);
		

		icvAPI.removeIntegrityConstraints(db.getServer(), db.getDatabaseName());
		return this.resultPath;
	}

	private void storeRuleViolationsInOntology(ArchitectureRule rule, Model codeModel, ConstraintViolationsResultSet result) {
		List<ConstraintViolation> violations = result.getViolations();

		for (ConstraintViolation violation : violations) 
		{
			ontology.storeConformanceCheckingResultForRule(codeModel, rule, violation.getAsserted());
		}
	}
	
	// the following methods are protected so that they can be used as a seam during testing
	protected Model loadModelFromFile(String filename) {
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(filename);
		return model;
	}
	
	protected void writeModelToFile(Model codemodel) {
		try 
		{	
			File file = new File(this.resultPath);			
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
