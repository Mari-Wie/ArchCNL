import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

import api.StardogICVAPI;
import conformancecheck.api.IConformanceCheck;
import conformancecheck.impl.ConformanceCheckImpl;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import mockups.StardogDatabaseMockup;
import mockups.StardogICVAPIMockup;

public class ConformanceCheckImplTest {
	
	public class ConformanceCheckImplSeam extends ConformanceCheckImpl {

		private boolean modelFileHasBeenWritten = false;
		
		public boolean modelFileHasBeenWritten() {
			return modelFileHasBeenWritten;
		}
		
		// Override this method so that no files must be read by the test
		@Override
		protected Model loadModelFromFile(String filename) {
			OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
			return model;
		}
		
		@Override
		protected void writeModelToFile(Model codemodel) {
			modelFileHasBeenWritten = true;
		}
		
	}
	
	private StardogDatabaseMockup _db;
	private StardogICVAPIMockup _icv;
	private ConformanceCheckImplSeam _iut; // instance under test
	private final String CONTEXT = "testcontext";
	private ArchitectureRule _rule;
	private final String PATH_TO_CONSTRAINT = "";
	private final int RULE_ID = 0;
	private final String MODEL_PATH = "testpath";
	
	@Before
	public void setUp() {
		_db = new StardogDatabaseMockup();
		_icv = new StardogICVAPIMockup();
		_iut = new ConformanceCheckImplSeam();
		_rule = new ArchitectureRule();
		_icv.setTestedRuleID(RULE_ID);
		_rule.setId(RULE_ID);
		_rule.setCnlSentence("Every Thing must use Thing.");
	}
	
	@Test
	public void testArchitectureRuleIsValidated() {
		ArchitectureRules.getInstance().addRuleWithPathToConstraint(_rule, RULE_ID, PATH_TO_CONSTRAINT);
		_iut.createNewConformanceCheck();
		_iut.validateRule(_rule, _db, CONTEXT, MODEL_PATH, _icv);
		
		assertTrue(_icv.constraintFileHasBeenAddedToServerAndDatabase(PATH_TO_CONSTRAINT, _db.getServer(), _db.getDatabaseName()));
//		assertEquals(0, _icv.timesCleared(_db.getServer(), _db.getDatabaseName()));
		
//		_iut.writeConformanceCheckingResultForRuleToFile(_rule, _db, CONTEXT, MODEL_PATH);

		assertTrue(_iut.modelFileHasBeenWritten());
		
		assertEquals(1, _icv.timesCleared(_db.getServer(), _db.getDatabaseName()));
	}

}
