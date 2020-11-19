package cnltoolchain;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import api.exceptions.MissingBuilderArgumentException;
import api.exceptions.NoConnectionToStardogServerException;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ArchCNL CLI", version = "ArchCNL CLI 0.0.1", mixinStandardHelpOptions = true) 
public class CNLToolchainCLI implements Runnable {
	
	private static final Logger LOG = LogManager.getLogger(CNLToolchainCLI.class);
	
	@Option(names = {"-d", "--database"}, description = "Specifiy the database name")
	private String database = "MWTest_"+  CNLToolchain.createTimeSuffix();
	
	@Option(names = {"-s", "--server"}, description = "Specifiy the server")
	private String server = "http://localhost:5820";
	
	@Option(names = {"-c", "--context"}, description = "Specifiy the context")
	private String context = "http://graphs.org/" + database + "/1.0";
	
	@Parameters(paramLabel = "<project path>", description = "The path to the project")
	private String projectPath;
	
	@Parameters(paramLabel = "<rules file>", description = "The path to the rules, relative to the project path")
	private String rulesFile;

	@Override
	public void run() {
    	LOG.info("Initializing ...");
    	
    	String rulesFileInProject = projectPath + rulesFile;
        
        LOG.info("Database     : "+database);
        LOG.info("Server       : "+server);
        LOG.info("Context      : "+context);
        LOG.info("Project Path : "+projectPath);
        LOG.info("RulesFile    : "+rulesFileInProject);
        
        CNLToolchain tool = new CNLToolchain(database, server);
        LOG.info("CNLToolchain initialized.");
        
        try
        {
            tool.execute(rulesFileInProject, projectPath, context);
            LOG.info("CNLToolchain completed successfully!");
        }
        catch (FileNotFoundException e)
        {
            LOG.error("File not found", e);
        }
        catch (MissingBuilderArgumentException e)
        {
            LOG.error("Missing builder argument", e);
        }
        catch (NoConnectionToStardogServerException e)
        {
        	LOG.error("No connection to stardog", e);
        }
	}
	
	public static void main(String[] args) {
        int exitCode = new CommandLine(new CNLToolchainCLI()).execute(args); 
        System.exit(exitCode); 
    }

}
