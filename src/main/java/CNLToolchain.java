import java.io.FileNotFoundException;
import java.util.List;

import api.ExecuteMappingAPI;
import api.ExecuteMappingAPIFactory;
import api.JavaCodeOntologyAPIImpl;
import api.ReasoningConfiguration;
import api.StardogICVAPI;
import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;
import asciidocparser.AsciiDocArc42Parser;
import conformancecheck.api.IConformanceCheck;
import conformancecheck.impl.ConformanceCheckImpl;
import core.OwlifyComponent;
import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import impl.StardogAPIFactory;
import impl.StardogDatabase;
import impl.StardogDatabase.StardogDatabaseBuilder;
import parser.FamixOntologyTransformer;

public class CNLToolchain {

	private List<String> ontologyPaths;
	//private OwlifyComponent javaOWLTransformer;
	private OwlifyComponent famixTransformer;
	private ExecuteMappingAPI mappingAPI;
	private StardogICVAPI icvAPI;
	
	private String databaseName;
	private String server; 

	public CNLToolchain(String databaseName, String server) {
		this.databaseName = databaseName;
		this.server = server;
		this.icvAPI = StardogAPIFactory.getICVAPI();
		this.famixTransformer = new FamixOntologyTransformer();
	}
	
	public void execute(String docPath, String sourceCodePath, String context)
			throws MissingBuilderArgumentException, FileNotFoundException, NoConnectionToStardogServerException {
		AsciiDocArc42Parser parser = new AsciiDocArc42Parser();
		parser.parseRulesFromDocumentation(docPath);
		parser.parseMappingRulesFromDocumentation(docPath);
		ontologyPaths = parser.getOntologyPaths();
		

		// Source Code Transformation
		famixTransformer.setSource(sourceCodePath);
		famixTransformer.transform();
		//javaOWLTransformer = new JavaCodeOntologyAPIImpl();
		//javaOWLTransformer.setSource(sourceCodePath);
		//javaOWLTransformer.transform();

		// Mapping
		mappingAPI = ExecuteMappingAPIFactory.get();
		ReasoningConfiguration reasoningConfig = ReasoningConfiguration.build().addPathsToConcepts(ontologyPaths)
				.withMappingRules(parser.getMappingFilePath()).withData(famixTransformer.getResultPath());
		mappingAPI.setReasoningConfiguration(reasoningConfig);
		mappingAPI.executeMapping();

		//create stardog db
		StardogDatabase db = new StardogDatabaseBuilder().server(this.server)
				.databaseName(databaseName).userName("admin").password("admin").createStardogDatabase();
		db.connect();
		// Load code to stardog and perform conformance checking
		db.addDataByRDFFileAsNamedGraph(mappingAPI.getReasoningResultPath(), context); //TODO ConformanceCheck component?

		IConformanceCheck check = new ConformanceCheckImpl(icvAPI);
		check.createNewConformanceCheck();
		for (ArchitectureRule rule : ArchitectureRules.getInstance().getRules().keySet()) {
			check.storeArchitectureRule(rule);
			check.validateRule(rule, db, context);
			check.storeConformanceCheckingResultInDatabaseForRule(rule, db, context);
		}
	}
	
	public static void main(String[] args) {
		String database = "test";
		String context = "http://graphs.org/" + database + "/v3.0";
		CNLToolchain tool = new CNLToolchain("test", "http://localhost:5820");
		try {
			tool.execute("./arc42-building-block-view.adoc", "C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl_test\\TestProject", context);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingBuilderArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionToStardogServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
