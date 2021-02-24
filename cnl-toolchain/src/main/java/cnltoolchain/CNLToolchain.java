package cnltoolchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.ConstraintViolationsResultSet;
import api.ExecuteMappingAPI;
import api.ExecuteMappingAPIFactory;
import api.ReasoningConfiguration;
import api.StardogDatabaseAPI;
import api.StardogICVAPI;
import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;
import asciidocparser.AsciiDocArc42Parser;
import conformancecheck.api.IConformanceCheck;
import conformancecheck.api.CheckedRule;
import conformancecheck.impl.ConformanceCheckImpl;
import core.OwlifyComponent;
import datatypes.ArchitectureRule;
import impl.StardogAPIFactory;
import impl.StardogDatabase;
import parser.FamixOntologyTransformer;

public class CNLToolchain {
	private static final Logger LOG = LogManager.getLogger(CNLToolchain.class);

	private OwlifyComponent famixTransformer;
	private ExecuteMappingAPI mappingAPI;
	private StardogICVAPI icvAPI;
	private IConformanceCheck check;
	private StardogDatabaseAPI db;

	private final String TEMPORARY_DIRECTORY = "./temp";
	private final String MAPPING_FILE_PATH = TEMPORARY_DIRECTORY + "/mapping.txt";

	// private, use runToolchain to create and execute the toolchain
	private CNLToolchain(String databaseName, String server, String username, String password) {
		this.db = new StardogDatabase(server, databaseName, username, password);
		this.icvAPI = StardogAPIFactory.getICVAPI(db);
		this.famixTransformer = new FamixOntologyTransformer(TEMPORARY_DIRECTORY + "/results.owl");
		this.check = new ConformanceCheckImpl();
	}

	/**
	 * Creates a Toolchain and executes it.
	 * 
	 * @param database    The name of the database to use.
	 * @param server      The hostname of the database server to connect to.
	 * @param context     The OWL context to use.
	 * @param username 	  The username to use when connecting to the database server.
	 * @param password    The password to use when connecting to the database server. 
	 * @param projectDirectory The path to the root of the project which should be
	 *                    analysed.
	 * @param rulesFile   The relative path to the AsciiDoc file which contains both the
	 *                    architecture and mapping rules.
	 */
	public static void runToolchain(String database, String server, String context, 
			String username, String password, String projectDirectory, String rulesFile) {
		LOG.debug("Database     : " + database);
		LOG.debug("Server       : " + server);
		LOG.debug("Context      : " + context);
		LOG.debug("Project path : " + projectDirectory);
		LOG.debug("RulesFile    : " + rulesFile);

		CNLToolchain tool = new CNLToolchain(database, server, username, password);
		LOG.info("CNLToolchain initialized.");

		try {
			Path projectPath = Paths.get(projectDirectory);
			Path rulesPath = Paths.get(projectDirectory, rulesFile);
			tool.execute(rulesPath, projectPath, context);
		} catch (FileNotFoundException e) {
			LOG.error("File not found", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoConnectionToStardogServerException e) {
			LOG.error("No connection to stardog", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String createTimeSuffix() {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
		return dateFormat.format(date);
	}

	/**
	 * Executes the ArchCNL tool chain.
	 * 
	 * @param docPath        path to the file containing the architecture rules and
	 *                       the architecture-to-code mapping
	 * @param sourceCodePath path to the root of the project to analyse
	 * @param context        the database context to use
	 * @throws MissingBuilderArgumentException
	 * @throws FileNotFoundException                when a file (input or a
	 *                                              temporary one) cannot be
	 *                                              accessed
	 * @throws NoConnectionToStardogServerException when no connection to the
	 *                                              database can be established
	 */
	private void execute(Path docPath, Path sourceCodePath, String context)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		LOG.info("Starting the execution");
		
		if(Files.notExists(sourceCodePath)) {
			throw new FileNotFoundException("The source code folder could not be found: " + sourceCodePath);
		}
		
		if(!Files.isDirectory(sourceCodePath)) {
			throw new FileNotFoundException("The source code folder is not a valid directory: " + sourceCodePath);
		}
		
		if(Files.notExists(docPath)) {
			throw new FileNotFoundException("The rules file could not be found: " + docPath);
		}

		createTemporaryDirectory();

		List<ArchitectureRule> rules = parseRuleFile(docPath);

		String codeModelPath = buildCodeModel(sourceCodePath);

		combineArchitectureAndCodeModels(rules, codeModelPath);

		LOG.info("Starting conformance checking...");
		
		LOG.debug("Connecting to the database ...");
		db.connect();

		storeModelAndConstraintsInDB(context, rules, mappingAPI.getReasoningResultPath());
		
		List<ConstraintViolationsResultSet> violations = icvAPI.explainViolationsForContext(context);
		icvAPI.removeIntegrityConstraints();
		
		String resultPath = TEMPORARY_DIRECTORY + "/check.owl";
		
		addViolationsToOntology(mappingAPI.getReasoningResultPath(), rules, violations, resultPath);
		
		db.addDataByRDFFileAsNamedGraph(resultPath, context);

		LOG.info("CNLToolchain completed successfully!");
	}

	private List<String> extractRuleOntologyPaths(List<ArchitectureRule> rules) {
		return rules.stream().map((ArchitectureRule r) -> {
			return r.getContraintFile();
		}).collect(Collectors.toList());
	}

	private void storeModelAndConstraintsInDB(String context, List<ArchitectureRule> rules, String modelPath)
			throws FileNotFoundException, NoConnectionToStardogServerException {
		LOG.debug("Adding the mapped model to the database...");
		db.addDataByRDFFileAsNamedGraph(modelPath, context); 

		List<ArchitectureRule> sucessfulRules = new ArrayList<>();
		
		for (ArchitectureRule rule : rules) { 
			
			String path = rule.getContraintFile();

			LOG.info("Checking the rule: " + rule.getCnlSentence());

			try {
				icvAPI.addIntegrityConstraint(path);
				sucessfulRules.add(rule);
			} catch (FileNotFoundException e) {
				LOG.error(e.getMessage() + " : " + path);
				LOG.warn("The following rule will not be stored in the database because its constraint file could not be accessed: " + rule.getCnlSentence());
			}
		}
		rules.clear();
		rules.addAll(sucessfulRules);
	}

	private void addViolationsToOntology(String tempfile, List<ArchitectureRule> sucessfulRules,
			List<ConstraintViolationsResultSet> violations, String resultPath) throws FileNotFoundException {
		check.createNewConformanceCheck();
		int ruleIndex = 0;
		for (ArchitectureRule rule : sucessfulRules) {
			
			CheckedRule vr = new CheckedRule(rule, violations.get(ruleIndex).getViolationList());
			
			
			if (!vr.getViolations().isEmpty()) {
				LOG.info("The following rule is violated: " + vr.getRule().getCnlSentence());
			}
			
			check.validateRule(vr, tempfile, resultPath);
			ruleIndex++;
		}
	}

	private void createTemporaryDirectory() {
		LOG.debug("Creating temporary directory " + TEMPORARY_DIRECTORY);
		File directory = new File(TEMPORARY_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private void combineArchitectureAndCodeModels(List<ArchitectureRule> rules,
			String codeModelPath) throws FileNotFoundException {
		LOG.info("Peforming the architecture-to-code mapping");
		List<String> ontologyPaths = extractRuleOntologyPaths(rules);
		mappingAPI = ExecuteMappingAPIFactory.get();
		
		ReasoningConfiguration reasoningConfig = ReasoningConfiguration.builder()
				.withPathsToConcepts(ontologyPaths)
				.withMappingRules(MAPPING_FILE_PATH)
				.withData(codeModelPath)
				.withResult(TEMPORARY_DIRECTORY + "/mapped.owl")
				.build();
		
		mappingAPI.setReasoningConfiguration(reasoningConfig);
		mappingAPI.executeMapping();
	}

	private String buildCodeModel(Path sourceCodePath) {
		LOG.info("Creating the code model ...");
		LOG.info("Starting famix transformation ...");
		// source code transformation
		famixTransformer.addSourcePath(sourceCodePath);
		famixTransformer.transform();
		String codeModelPath = famixTransformer.getResultPath();
		return codeModelPath;
	}

	private List<ArchitectureRule> parseRuleFile(Path docPath) {
		LOG.info("Parsing the rule file ...");
		AsciiDocArc42Parser parser = new AsciiDocArc42Parser(gatherOWLNamespaces());
		LOG.debug("Parsing the architecture rules ...");
		List<ArchitectureRule> rules = parser.parseRulesFromDocumentation(docPath, TEMPORARY_DIRECTORY);
		LOG.debug("Parsing the mapping rules ...");
		parser.parseMappingRulesFromDocumentation(docPath, MAPPING_FILE_PATH);

		return rules;
	}

	private HashMap<String, String> gatherOWLNamespaces() {
		HashMap<String, String> supportedOWLNamespaces = new HashMap<>();
		supportedOWLNamespaces.putAll(famixTransformer.getProvidedNamespaces());
		supportedOWLNamespaces.putAll(check.getProvidedNamespaces());
		supportedOWLNamespaces.put("architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");
		return supportedOWLNamespaces;
	}
}
