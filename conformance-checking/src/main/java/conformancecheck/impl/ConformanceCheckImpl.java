package conformancecheck.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.jena.rdf.model.Model;

import com.google.inject.Inject;

import api.StardogConstraintViolation;
import api.StardogConstraintViolationsResultSet;
import api.StardogDatabaseInterface;
import api.StardogICVAPI;
import api.exceptions.NoConnectionToStardogServerException;
import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

public class ConformanceCheckImpl implements IConformanceCheck 
{
	private static final Logger LOG = LogManager.getLogger(ConformanceCheckImpl.class);
	
	private StardogICVAPI icvAPI;

	private ConformanceCheckOntology ontology;

	private String resultPath;

	private StardogConstraintViolationsResultSet result;

	@Inject
	public ConformanceCheckImpl(StardogICVAPI icvAPI) 
	{
		this.icvAPI = icvAPI;

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

//	@Override
//	public void storeArchitectureRule(ArchitectureRule rule) 
//	{
//    	LOG.info("Starting storeArchitectureRule ...");
//		ontology.storeArchitectureRule(rule);
//	}

	@Override
	public void validateRule(ArchitectureRule rule, StardogDatabaseInterface db, String context) 
	{
		// TODO: remove dependency on StardogConnector
		// TODO: change to a single method or express the required workflow in another, better way
		//		 than having to call several methods in a fixed order (createNewConformanceCheck, validateRule, store...).
    	LOG.info("Starting validateRule ...");
    	ontology.storeArchitectureRule(rule);
		String path = ArchitectureRules.getInstance().getPathOfConstraintForRule(rule); // TODO: remove dependency on the singleton
		String constraint;
		try 
		{
			constraint = icvAPI.addIntegrityConstraint(path, db.getServer(), db.getDatabaseName());
			icvAPI.explainViolationsForContext(db.getServer(), db.getDatabaseName(), context);
			rule.setStardogConstraint(constraint);
			this.result = icvAPI.getResult();
		}
		catch (FileNotFoundException e) 
		{
			LOG.error(e.getMessage()+ " : " + path);
		}

	}

	@Override
	public void storeConformanceCheckingResultInDatabaseForRule(ArchitectureRule rule, StardogDatabaseInterface db,
			String context) 
	{
    	LOG.info("Starting storeConformanceCheckingResultInDatabaseForRule: " + rule.getCnlSentence());
		List<StardogConstraintViolation> violations = result.getViolations();
		
		// TODO connects the code model with conformance check instances
		// Model model = connectionAPI.getModelFromContext(context);
		Model model = db.getModelFromContext(context);
		CodeModel codeModel = new CodeModel(context, model);

		for (StardogConstraintViolation violation : violations) 
		{
			ontology.storeConformanceCheckingResultForRule(codeModel, rule, violation);
		}
		
		try 
		{
			saveResultsToDatabase(db, context);
		} 
		catch (FileNotFoundException e) 
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		} 
		catch (NoConnectionToStardogServerException e) 
		{
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		icvAPI.removeIntegrityConstraints(db.getServer(), db.getDatabaseName());
	}

	private void saveResultsToDatabase(StardogDatabaseInterface db, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException 
	{
    	LOG.info("Starting saveResultsToDatabase ...");
		Model codemodel = db.getModelFromContext(context);
    	
    	LOG.info("add model to code model");
		codemodel.add(ontology.getModel());
		File file = new File(this.resultPath);
		
		LOG.info("write to code model");
		codemodel.write(new FileOutputStream(file));
		
		LOG.info("add data to database");
		db.addDataByRDFFileAsNamedGraph(this.resultPath, context);
	}

}
