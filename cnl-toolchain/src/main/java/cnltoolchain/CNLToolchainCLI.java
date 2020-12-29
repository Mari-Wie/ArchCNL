package cnltoolchain;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ArchCNL CLI", version = "ArchCNL CLI 0.0.1", mixinStandardHelpOptions = true) 
public class CNLToolchainCLI implements Runnable {
	
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
        CNLToolchain.runToolchain(database, server, context, projectPath, projectPath + rulesFile);
	}
	
	public static void main(String[] args) {
        int exitCode = new CommandLine(new CNLToolchainCLI()).execute(args); 
        System.exit(exitCode); 
    }

}
