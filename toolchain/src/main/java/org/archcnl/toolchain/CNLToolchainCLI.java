package org.archcnl.toolchain;

import java.util.Arrays;
import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ArchCNL CLI", version = "ArchCNL CLI 0.0.1", mixinStandardHelpOptions = true)
public class CNLToolchainCLI implements Runnable {

    @Option(
            names = {"-d", "--database"},
            description = "Specifiy the database name")
    private String database = "ArchCNL_" + CNLToolchain.createTimeSuffix();

    @Option(
            names = {"-s", "--server"},
            description = "Specifiy the server")
    private String server = "http://localhost:5820";

    @Option(
            names = {"-c", "--context"},
            description = "Specifiy the context")
    private String context = "http://graphs.org/" + database + "/1.0";

    @Parameters(paramLabel = "<rules file>", description = "The path to the rules", index = "0")
    private String rulesFile;

    @Parameters(
            paramLabel = "<project path>",
            description =
                    "One or more paths to the project's source code root directories (usually some kind of \"src\" folder)",
            index = "1..*")
    private List<String> projectPaths;

    @Option(
            names = {"-u", "--username"},
            description = "Specifiy the username for the database server")
    private String username = "admin";

    @Option(
            names = {"-p", "--password"},
            description = "Specifiy the password for the database server")
    private String password = "admin";

    @Option(
            names = {"-v", "--verbose"},
            description = "Set the log level to ALL and output all log messages")
    boolean logVerbose;

    @Option(
            names = {"-rm-db", "--remove-databases"},
            description = "Remove previous databases before adding new ones")
    boolean removePreviousDatabases;

    @Option(
            names = {"--parsers"},
            description = "Specify the enabled parsers (comma-separated list)")
    private String parsers = "java,kotlin";

    @Override
    public void run() {
        var parsersList = Arrays.asList(parsers.split(","));
        CNLToolchain.runToolchain(
                database,
                server,
                context,
                username,
                password,
                projectPaths,
                rulesFile,
                logVerbose,
                removePreviousDatabases,
                parsersList);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CNLToolchainCLI()).execute(args);
        System.exit(exitCode);
    }
}
