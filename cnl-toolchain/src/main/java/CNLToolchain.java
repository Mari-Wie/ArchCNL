import java.io.FileNotFoundException;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;   

import api.ExecuteMappingAPI;
import api.ExecuteMappingAPIFactory;
import api.ReasoningConfiguration;
import api.StardogDatabaseInterface;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
public class CNLToolchain
{
	private static final Logger LOG = LogManager.getLogger(CNLToolchain.class);

    private List<String> ontologyPaths;
    //private OwlifyComponent javaOWLTransformer;
    private OwlifyComponent famixTransformer;
    private ExecuteMappingAPI mappingAPI;
    private StardogICVAPI icvAPI;

    private String databaseName;
    private String server;
    
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
        this.icvAPI = StardogAPIFactory.getICVAPI();
        this.famixTransformer = new FamixOntologyTransformer();
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
        String database = "MWTest_"+createTimeSuffix();  
        String server =  "http://localhost:5820";
        String context = "http://graphs.org/" + database + "/1.0";
        String projectPath = "/home/user/study/shk-swk/code/OnionArchitectureDemo/";
        String rulesFile = projectPath + "architecture-documentation-onion.adoc";
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

	private static String createTimeSuffix() {
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");  
        String strDate = dateFormat.format(date);
		return strDate;
	}
    
    
    public void execute(String docPath, String sourceCodePath, String context)
            throws MissingBuilderArgumentException, FileNotFoundException,
            NoConnectionToStardogServerException
    {	
    	LOG.info("Start execution.");

    	LOG.info("Start parsing...");
        AsciiDocArc42Parser parser = new AsciiDocArc42Parser();
        parser.parseRulesFromDocumentation(docPath);
        parser.parseMappingRulesFromDocumentation(docPath);
        ontologyPaths = parser.getOntologyPaths();

    	LOG.info("Start famix transformation...");
        // Source Code Transformation
        famixTransformer.addSourcePath(sourceCodePath);
        famixTransformer.transform();
        //javaOWLTransformer = new JavaCodeOntologyAPIImpl();
        //javaOWLTransformer.setSource(sourceCodePath);
        //javaOWLTransformer.transform();

        // Mapping
    	LOG.info("Start get mappings...");
        mappingAPI = ExecuteMappingAPIFactory.get();
        ReasoningConfiguration reasoningConfig = ReasoningConfiguration.build()
            .addPathsToConcepts(ontologyPaths)
            .withMappingRules(parser.getMappingFilePath())
            .withData(famixTransformer.getResultPath());
        mappingAPI.setReasoningConfiguration(reasoningConfig);
        mappingAPI.executeMapping();

        //create stardog db
    	LOG.info("Create StardogDB ...");
//        StardogDatabase db = new StardogDatabaseBuilder().server(server)
//            .databaseName(databaseName)
//            .userName("admin")
//            .password("admin")
//            .createStardogDatabase();
        StardogDatabaseInterface db = new StardogDatabase(server,databaseName,"admin","admin");
    	LOG.info("Connect to StardogDB ...");
        db.connect();
        
        // Load code to stardog and perform conformance checking
    	LOG.info("Start reasoning...");
        db.addDataByRDFFileAsNamedGraph(mappingAPI.getReasoningResultPath(),
                context); //TODO ConformanceCheck component?

    	LOG.info("Start conformance checking...");
        IConformanceCheck check = new ConformanceCheckImpl(icvAPI);
        check.createNewConformanceCheck();
        for (ArchitectureRule rule : ArchitectureRules.getInstance()
            .getRules()
            .keySet())
        {
        	LOG.info("conformance checking rule: " + rule.getCnlSentence());
        	// TODO: rule hat offenbar keinen Type .... Recherche hier fortfuehren!
        //	LOG.info("conformance checking rule: " + rule.getType().toString());
            check.storeArchitectureRule(rule);
            check.validateRule(rule, db, context);
            check.storeConformanceCheckingResultInDatabaseForRule(rule, db,
                    context);
        }

    	LOG.info("End execution.");
    }



}
