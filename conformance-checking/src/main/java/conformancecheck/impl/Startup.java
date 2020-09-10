package conformancecheck.impl;

import java.io.FileNotFoundException;

import com.google.inject.Guice;
import com.google.inject.Injector;

//import api.StardogConnectionAPI;
import api.StardogICVAPI;
import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;
import conformancecheck.api.IConformanceCheck;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import datatypes.RuleType;
import impl.StardogDatabase;
import impl.StardogDatabase.StardogDatabaseBuilder;

public class Startup {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new ConformanceCheckModule());
		StardogICVAPI icvAPI = injector.getInstance(StardogICVAPI.class);

		try {
			StardogDatabase db = new StardogDatabaseBuilder().server("http://localhost:5820")
					.databaseName("test-database").userName("admin").password("admin").createStardogDatabase();
			db.connect();
			String context = "http://graphs.org/test-database/v1.0";
			IConformanceCheck check = new ConformanceCheckImpl(icvAPI);

			/* Test rule */
			ArchitectureRule rule = new ArchitectureRule();
			rule.setId(0);
			rule.setCnlSentence("Every LogicType must a DBType.");
			rule.setType(RuleType.EXISTENTIAL);
			ArchitectureRules.getInstance().addRule(rule, 0);
			ArchitectureRules.getInstance().addRuleWithPathToConstraint(rule, 0,
					"./src/test/resources/architecture0.owl");

			check.createNewConformanceCheck();
			check.storeArchitectureRule(rule);

			check.validateRule(rule, db, context);
			check.storeConformanceCheckingResultInDatabaseForRule(rule, db, context);
			try {
				check.saveResultsToDatabase(db, context);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoConnectionToStardogServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (

		MissingBuilderArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
