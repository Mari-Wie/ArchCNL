package org.archcnl.toolchain;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "ArchCNL CLI", version = "ArchCNL CLI 0.0.1", mixinStandardHelpOptions = true)
public class CNLToolchainCLI implements Runnable {

    @Option(
            names = {"-d", "--database"},
            description = "Specifiy the database name")
    private String database = "MWTest_" + CNLToolchain.createTimeSuffix();

    @Option(
            names = {"-s", "--server"},
            description = "Specifiy the server")
    private String server = "http://localhost:5820";

    @Option(
            names = {"-c", "--context"},
            description = "Specifiy the context")
    private String context = "http://graphs.org/" + database + "/1.0";

    @Parameters(paramLabel = "<project path>", description = "The path to the project")
    private String projectPath;

    @Parameters(
            paramLabel = "<rules file>",
            description = "The path to the rules, relative to the project path")
    private String rulesFile;

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

    @Override
    public void run() {
        CNLToolchain.runToolchain(
                database, server, context, username, password, projectPath, rulesFile, logVerbose);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CNLToolchainCLI()).execute(args);
        System.exit(exitCode);
    }
}
