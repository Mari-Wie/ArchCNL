package conformancecheck.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.jena.rdf.model.Model;

import com.google.inject.Inject;

import api.StardogConstraintViolation;
import api.StardogConstraintViolationsResultSet;
import api.StardogICVAPI;
import api.exceptions.NoConnectionToStardogServerException;
import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import impl.StardogDatabase;

public class ConformanceCheckImpl implements IConformanceCheck {

	private StardogICVAPI icvAPI;

	private ConformanceCheckOntology ontology;

	private String resultPath;

	private StardogConstraintViolationsResultSet result;

	@Inject
	public ConformanceCheckImpl(StardogICVAPI icvAPI) {
		this.icvAPI = icvAPI;

		String dir = "./conformance_checks/";
		new File(dir).mkdirs();
		this.resultPath = dir + "check.owl";
	}

	public void createNewConformanceCheck() {
		ontology = new ConformanceCheckOntology();
		ontology.newConformanceCheck();

	}

	public void storeArchitectureRule(ArchitectureRule rule) {
		ontology.storeArchitectureRule(rule);
	}

	public void validateRule(ArchitectureRule rule, StardogDatabase db, String context) {

		String path = ArchitectureRules.getInstance().getPathOfConstraintForRule(rule);
		String constraint;
		try {
			constraint = icvAPI.addIntegrityConstraint(rule.getId(), path, db.getServer(), db.getDatabaseName());
			icvAPI.explainViolationsForContext(db.getServer(), db.getDatabaseName(), context);
			rule.setStardogConstraint(constraint);
			this.result = icvAPI.getResult();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Path to constraint not found: " + path);
		}

	}

	public StardogConstraintViolationsResultSet getResult() {
		return result;
	}

	public void storeConformanceCheckingResultInDatabaseForRule(ArchitectureRule rule, StardogDatabase db,
			String context) {
		List<StardogConstraintViolation> violations = result.getViolations();
		// TODO connects the code model with conformance check instances
		// Model model = connectionAPI.getModelFromContext(context);
		Model model = db.getModelFromContext(context);
		CodeModel codeModel = new CodeModel(context, model);

		for (StardogConstraintViolation violation : violations) {
			ontology.storeConformanceCheckingResultForRule(codeModel, rule, violation);
		}
		
		try {
			saveResultsToDatabase(db, context);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionToStardogServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path = ArchitectureRules.getInstance().getPathOfConstraintForRule(rule);
		try {
			icvAPI.removeIntegrityConstraints(path, db.getServer(), db.getDatabaseName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void saveResultsToDatabase(StardogDatabase db, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		Model codemodel = db.getModelFromContext(context);
		System.out.println("add model to code model");
		codemodel.add(ontology.getModel());
		File file = new File(this.resultPath);
		System.out.println("write to code model");
		codemodel.write(new FileOutputStream(file));
		System.out.println("add data to database");
		db.addDataByRDFFileAsNamedGraph(this.resultPath, context);
	}

}
