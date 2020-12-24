package cnltoolchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.ExecuteMappingAPI;
import api.ExecuteMappingAPIFactory;
import api.ReasoningConfiguration;
import api.StardogDatabaseAPI;
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
//import impl.StardogDatabase.StardogDatabaseBuilder;
import parser.FamixOntologyTransformer;
 
public class CNLToolchain
{
	private static final Logger LOG = LogManager.getLogger(CNLToolchain.class);

    private OwlifyComponent famixTransformer;
    private ExecuteMappingAPI mappingAPI;
    private StardogICVAPI icvAPI;
    private IConformanceCheck check;
    private StardogDatabaseAPI db;
    
    private String databaseName;
    private String server;
    
    private final String TEMPORARY_DIRECTORY = "./temp";
    
    /**
     * Konstruktur f�r CNLToolchain
     * 
     * Zugriffe auf 
     * - Stardog-API
     * - FamixTransformerAPI 
     * werden erstellt
     * 
     * @param databaseName	- Name der Stardog-DB, in der die Ontologien einschl. der Ergebnis-Ontologie abgelegt wird
     * @param server		- Server und Port der DB-Instanz der Datenbank
     */
    public CNLToolchain(String databaseName, String server)
    {
        this.databaseName = databaseName;
        this.server = server;
        this.db = new StardogDatabase(server,databaseName,"admin","admin");
        this.icvAPI = StardogAPIFactory.getICVAPI(db);
        this.famixTransformer = new FamixOntologyTransformer(TEMPORARY_DIRECTORY + "/results.owl");
        this.check = new ConformanceCheckImpl();
    }
    
    /**
     * Zentrale Methode zum Aufrufen des gesamten ConformanceChecking-Ablaufs
     * 
     * Alle Parameter werden hier gesetzt (Bisher werden keine Parameter �bergeben)
     * Eine CNLToolchain-Insatz wird erstellt.
     * Die execute()-Methode zur Durchf�rhung des ConformanceChecks eird aufgerufen
     * 
     * Diese Parameter sind:
     * - database 		Name der Stardog-DB
     * - server 		localhost und Port f�r den Zugriff auf die DB-Instanz
     * - context 		noch zu kl�ren
     * - projectPath	Projektpfad des zu �berpr�fenden Projekts
     * - rulesFile 		Name des Asciidoc-Files mit Regeln und Mappings
     * 
     * @param args
     */
    public static void main(String[] args)
    {    	 
    	// TODO: Parameter von aussen uebergeben (z.B. ueber Parameter-File)
        LOG.info("Initializing ...");
        CNLToolchainProperties props = new CNLToolchainProperties();
        try {
			props.readPropValues();
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
			return;
		}
        
        String database = "MWTest_"+createTimeSuffix();  
        String server =  props.getServer();
        String context = "http://graphs.org/" + database + "/1.0";
        String projectPath = props.getProjectPath();
        String rulesFile = projectPath + props.getRulesFile();
        LOG.info("Database     : "+database);
        LOG.info("Server       : "+server);
        LOG.info("Context      : "+context);
        LOG.info("Project Path : "+projectPath);
        LOG.info("RulesFile    : "+rulesFile);
        
        CNLToolchain tool = new CNLToolchain(database, server);
        LOG.info("CNLToolchain initialized.");
        
        try
        {
            tool.execute(rulesFile, projectPath, context);
            LOG.info("CNLToolchain completed successfully!");
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MissingBuilderArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoConnectionToStardogServerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	protected static String createTimeSuffix() {
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");  
        String strDate = dateFormat.format(date);
		return strDate;
	}
    
    /**
     * Executes the ArchCNL tool chain.
     * @param docPath path to the file containing the architecture rules and the architecture-to-code mapping
     * @param sourceCodePath path to the root of the project to analyse
     * @param context the database context to use
     * @throws MissingBuilderArgumentException
     * @throws FileNotFoundException when a file (input or a temporary one) cannot be accessed
     * @throws NoConnectionToStardogServerException when no connection to the database can be established
     */
    public void execute(String docPath, String sourceCodePath, String context)
            throws MissingBuilderArgumentException, FileNotFoundException,
            NoConnectionToStardogServerException
    {	
    	LOG.info("Start execution.");

    	LOG.info("Create temporary directory");
    	
    	File directory = new File(TEMPORARY_DIRECTORY);
        if (! directory.exists()){
            directory.mkdir();
        }
        
        final String mappingFilePath = TEMPORARY_DIRECTORY + "/mapping.txt";
      
    	List<String> ontologyPaths = parseRuleFile(docPath, mappingFilePath);
    	String codeModelPath = buildCodeModel(sourceCodePath);
    	
        performArchitectureToCodeMapping(mappingFilePath, ontologyPaths, codeModelPath);

        LOG.info("Connect to StardogDB ...");
        db.connect();
        
        // Load code to stardog and perform conformance checking
    	LOG.info("Start reasoning...");
        db.addDataByRDFFileAsNamedGraph(mappingAPI.getReasoningResultPath(),
                context); //TODO ConformanceCheck component?

        // TODO: check all rules at once?
    	LOG.info("Start conformance checking...");
        check.createNewConformanceCheck();
        for (ArchitectureRule rule : ArchitectureRules.getInstance()
            .getRules())
        {
        	
        	String path = ArchitectureRules.getInstance().getPathOfConstraintForRule(rule); // TODO: remove dependency on the singleton
    		
        	LOG.info("conformance checking rule: " + rule.getCnlSentence());
            String tempfile = TEMPORARY_DIRECTORY + "/tmp.owl";
            db.writeModelFromContextToFile(context, tempfile);
            
            try 
    		{
    			icvAPI.addIntegrityConstraint(path);
    		}
    		catch (FileNotFoundException e) 
    		{
    			LOG.error(e.getMessage()+ " : " + path);
    		}

    		icvAPI.explainViolationsForContext(context);    		
    		icvAPI.removeIntegrityConstraints();
            
            String resultPath = TEMPORARY_DIRECTORY + "/check.owl";
            check.validateRule(rule, tempfile, icvAPI.getResult(), resultPath); // TODO use return value
            db.addDataByRDFFileAsNamedGraph(resultPath, context);
        }

    	LOG.info("End execution.");
    }

	private void performArchitectureToCodeMapping(final String mappingFilePath, List<String> ontologyPaths,
			String codeModelPath) throws FileNotFoundException {
		// Mapping
    	LOG.info("Start get mappings...");
        mappingAPI = ExecuteMappingAPIFactory.get();
        ReasoningConfiguration reasoningConfig = ReasoningConfiguration.build()
            .withPathsToConcepts(ontologyPaths)
            .withMappingRules(mappingFilePath)
            .withData(codeModelPath);
        mappingAPI.setReasoningConfiguration(reasoningConfig, TEMPORARY_DIRECTORY + "/mapped.owl");
        mappingAPI.executeMapping();
	}

	private String buildCodeModel(String sourceCodePath) {
		LOG.info("Start famix transformation...");
        // Source Code Transformation
        famixTransformer.addSourcePath(sourceCodePath);
        famixTransformer.transform();
        String codeModelPath = famixTransformer.getResultPath();
		return codeModelPath;
	}

	private List<String> parseRuleFile(String docPath, final String mappingFilePath) {
		LOG.info("Start parsing...");
        AsciiDocArc42Parser parser = new AsciiDocArc42Parser(gatherOWLNamespaces());
        parser.parseRulesFromDocumentation(docPath, TEMPORARY_DIRECTORY);
        parser.parseMappingRulesFromDocumentation(docPath, mappingFilePath);
        List<String> ontologyPaths = parser.getOntologyPaths();
		return ontologyPaths;
	}

	private HashMap<String, String> gatherOWLNamespaces() {
		HashMap<String, String> supportedOWLNamespaces = new HashMap<>();
        
        supportedOWLNamespaces.putAll(famixTransformer.getProvidedNamespaces());
        supportedOWLNamespaces.putAll(check.getProvidedNamespaces());
        supportedOWLNamespaces.put("architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");
		return supportedOWLNamespaces;
	}



}
