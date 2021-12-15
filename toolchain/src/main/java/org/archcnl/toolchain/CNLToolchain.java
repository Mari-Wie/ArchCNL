package org.archcnl.toolchain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.jena.rdf.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.archcnl.architecturedescriptionparser.AsciiDocArc42Parser;
import org.archcnl.architecturereasoning.api.ExecuteMappingAPI;
import org.archcnl.architecturereasoning.api.ExecuteMappingAPIFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.ConstraintViolation;
import org.archcnl.conformancechecking.api.CheckedRule;
import org.archcnl.conformancechecking.api.IConformanceCheck;
import org.archcnl.conformancechecking.impl.ConformanceCheckImpl;
import org.archcnl.javaparser.parser.JavaOntologyTransformer;
import org.archcnl.kotlinparser.parser.KotlinOntologyTransformer;
import org.archcnl.owlify.core.OwlifyComponent;
import org.archcnl.stardogwrapper.api.ConstraintViolationsResultSet;
import org.archcnl.stardogwrapper.api.StardogAPIFactory;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.api.exceptions.DBAccessException;
import org.archcnl.stardogwrapper.api.exceptions.NoConnectionToStardogServerException;
import org.archcnl.stardogwrapper.impl.StardogDatabase;

public class CNLToolchain {
    private static final Logger LOG = LogManager.getLogger(CNLToolchain.class);
    private static final String TEMPORARY_DIRECTORY = "./temp";
    private static final String MAPPING_FILE_PATH = TEMPORARY_DIRECTORY + "/mapping.txt";
    private static final String MAPPED_ONTOLOGY_PATH = TEMPORARY_DIRECTORY + "/mapped.owl";
    private final List<OwlifyComponent> transformers;
    private final StardogICVAPI icvAPI;
    private final IConformanceCheck check;
    private final StardogDatabaseAPI db;
    private Map<ArchitectureRule, ConstraintViolationsResultSet> ruleToViolationMapping =
            new HashMap<ArchitectureRule, ConstraintViolationsResultSet>();

    // mapping of name to transformer factories
    // add new parsers here
    private final Map<String, Supplier<OwlifyComponent>> transformerFactories =
            Map.ofEntries(
                    Map.entry("java", () -> new JavaOntologyTransformer()),
                    Map.entry("kotlin", () -> new KotlinOntologyTransformer()));

    // private, use runToolchain to create and execute the toolchain
    private CNLToolchain(
            String databaseName,
            String server,
            String username,
            String password,
            List<String> enabledTransformers) {
        this.db = new StardogDatabase(server, databaseName, username, password);
        this.icvAPI = StardogAPIFactory.getICVAPI(db);
        this.transformers =
                enabledTransformers.stream()
                        .map(name -> createTransformer(name))
                        .collect(Collectors.toList());
        this.check = new ConformanceCheckImpl();
    }

    /**
     * Creates a Toolchain and executes it.
     *
     * @param database The name of the database to use.
     * @param server The hostname of the database server to connect to.
     * @param context The OWL context to use.
     * @param username The username to use when connecting to the database server.
     * @param password The password to use when connecting to the database server.
     * @param projectPathsAsString One or more paths to the root of the project's Java source code
     *     which should be analysed (usually some kind of "src" folder in the project).
     * @param rulesFile The path to the AsciiDoc file which contains both the architecture and
     *     mapping rules.
     * @param logVerbose If all log levels down to trace should be logged in file and on the console
     * @param removePreviousDatabases If all previous databases should be permanently removed
     * @param enabledParsers The names of all enabled parsers.
     */
    public static void runToolchain(
            String database,
            String server,
            String context,
            String username,
            String password,
            List<String> projectPathsAsString,
            String rulesFile,
            boolean logVerbose,
            boolean removePreviousDatabases,
            List<String> enabledParsers) {

        if (logVerbose) {
            LOG.info("verbose logging is enabled");
            ThreadContext.put("verboseMode", "true");
        } else {
            ThreadContext.put("verboseMode", "false");
        }

        if (!isDatabaseNameValid(database)) {
            LOG.fatal(
                    "Databasename: {} is not possible, the database name must start with an alpha character followed by any alphanumeric character, dash or underscore.",
                    database);
            return;
        }

        if (projectPathsAsString == null || projectPathsAsString.isEmpty()) {
            LOG.fatal("There are no project paths provided");
            return;
        }

        LOG.debug("Database     : " + database);
        LOG.debug("Server       : " + server);
        LOG.debug("Context      : " + context);
        LOG.info("Project path : " + projectPathsAsString);
        LOG.debug("RulesFile    : " + rulesFile);

        CNLToolchain tool = new CNLToolchain(database, server, username, password, enabledParsers);
        LOG.info("CNLToolchain initialized.");

        try {
            List<Path> projectPaths = new ArrayList<>();
            for (var projectPathAsString : projectPathsAsString) {
                projectPaths.add(Paths.get(projectPathAsString));
            }
            Path rulesPath = Paths.get(rulesFile);
            tool.execute(rulesPath, projectPaths, context, removePreviousDatabases);
        } catch (FileNotFoundException e) {
            LOG.fatal("File not found", e);
            e.printStackTrace();
        } catch (NoConnectionToStardogServerException e) {
            LOG.fatal("No connection to stardog", e);
            e.printStackTrace();
        } catch (IOException e) {
            LOG.fatal("An I/O operation failed.", e);
        }
    }

    static String createTimeSuffix() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd__'at'__HH-mm-ss_z");
        return dateFormat.format(date);
    }

    /*
     * Match the databaseName to a regex provided by Stardog
     *
     * @see <a href="https://docs.stardog.com/operating-stardog/database-administration/database-configuration#databasename">Stardog Documentation</a>
     */
    private static boolean isDatabaseNameValid(String databaseName) {
        var pattern = Pattern.compile("[A-Za-z]{1}[A-Za-z0-9_-]*");
        var matcher = pattern.matcher(databaseName);
        return matcher.matches();
    }

    private OwlifyComponent createTransformer(String name) {
        var factory = transformerFactories.get(name);
        if (factory == null) throw new RuntimeException("Unknown parser '" + name + "'");
        return factory.get();
    }

    /**
     * Executes the ArchCNL tool chain.
     *
     * @param docPath path to the file containing the architecture rules and the
     *     architecture-to-code mapping
     * @param sourceCodePaths One or more paths to the root of the project to analyse
     * @param context the database context to use
     * @throws FileNotFoundException when a file (input or a temporary one) cannot be accessed
     * @throws NoConnectionToStardogServerException when no connection to the database can be
     *     established
     */
    private void execute(
            Path docPath,
            List<Path> sourceCodePaths,
            String context,
            boolean removePreviousDatabases)
            throws NoConnectionToStardogServerException, IOException {
        LOG.info("Starting the execution");

        for (var sourceCodePath : sourceCodePaths) {
            if (Files.notExists(sourceCodePath)) {
                throw new FileNotFoundException(
                        "The source code folder could not be found: " + sourceCodePath);
            }

            if (!Files.isDirectory(sourceCodePath)) {
                throw new FileNotFoundException(
                        "The source code folder is not a valid directory: " + sourceCodePath);
            }
        }

        if (Files.notExists(docPath)) {
            throw new FileNotFoundException("The rules file could not be found: " + docPath);
        }

        createTemporaryDirectory();
        List<ArchitectureRule> rules;
        try {
            rules = parseRuleFile(docPath);
        } catch (IOException e) {
            throw new IOException("Rules could not be parsed", e);
        }
        Model codeModel = buildCodeModel(sourceCodePaths);

        combineArchitectureAndCodeModels(rules, codeModel);

        LOG.info("Starting conformance checking...");

        LOG.debug("Connecting to the database ...");
        db.connect(removePreviousDatabases);

        storeModelAndConstraintsInDB(context, rules, MAPPED_ONTOLOGY_PATH);

        String resultPath = TEMPORARY_DIRECTORY + "/check.owl";

        addViolationsToOntology(MAPPED_ONTOLOGY_PATH, ruleToViolationMapping, resultPath);

        db.addDataByRDFFileAsNamedGraph(resultPath, context);

        LOG.info("CNLToolchain completed successfully!");
    }

    private void storeModelAndConstraintsInDB(
            String context, List<ArchitectureRule> rules, String modelPath)
            throws FileNotFoundException, NoConnectionToStardogServerException {
        LOG.debug("Adding the mapped model to the database...");
        db.addDataByRDFFileAsNamedGraph(modelPath, context);

        for (ArchitectureRule rule : rules) {
            String path = TEMPORARY_DIRECTORY + "/architecture" + rule.getId() + ".owl";

            LOG.info("Checking the rule: " + rule.getCnlSentence());

            try {
                icvAPI.addIntegrityConstraint(rule.getRuleModel());
                ruleToViolationMapping.put(
                        rule, icvAPI.explainViolationsForContext(context).get(0));
                icvAPI.removeIntegrityConstraints();
            } catch (DBAccessException e) {
                LOG.error(e.getMessage());
                LOG.warn(
                        "The following rule will not be stored in the database because its constraint could not be added to the database: "
                                + rule.getCnlSentence()
                                + " stored in "
                                + path);
            }
        }
        rules.clear();
    }

    private void addViolationsToOntology(
            String tempfile,
            Map<ArchitectureRule, ConstraintViolationsResultSet> violations,
            String resultPath)
            throws FileNotFoundException {
        check.createNewConformanceCheck();
        for (ArchitectureRule rule : violations.keySet()) {
            List<ConstraintViolation> violationsForRule;
            violationsForRule = violations.get(rule).getViolationList();

            CheckedRule vr = new CheckedRule(rule, violationsForRule);

            if (!vr.getViolations().isEmpty()) {
                LOG.info("The following rule is violated: " + vr.getRule().getCnlSentence());
            }

            check.validateRule(vr, tempfile, resultPath);
        }
    }

    private void createTemporaryDirectory() {
        LOG.debug("Creating temporary directory " + TEMPORARY_DIRECTORY);
        File directory = new File(TEMPORARY_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void combineArchitectureAndCodeModels(List<ArchitectureRule> rules, Model codeModel)
            throws IOException {
        LOG.info("Peforming the architecture-to-code mapping");
        ExecuteMappingAPI mappingAPI = ExecuteMappingAPIFactory.get();

        Model mappedModel = mappingAPI.executeMapping(codeModel, rules, MAPPING_FILE_PATH);

        try (FileWriter writer = new FileWriter(MAPPED_ONTOLOGY_PATH)) {
            mappedModel.write(writer);
        } catch (IOException e) {
            LOG.fatal("Writing the mapped model to \"" + MAPPED_ONTOLOGY_PATH + "\" failed: ", e);
            throw e;
        }
    }

    private Model buildCodeModel(List<Path> sourceCodePaths) {
        LOG.info("Creating the code model ...");
        LOG.info("Starting famix transformation ...");
        var model =
                transformers.stream()
                        .map(transformer -> transformer.transform(sourceCodePaths))
                        .reduce(
                                null,
                                (modelA, modelB) -> modelA == null ? modelB : modelA.union(modelB));
        return model;
    }

    private List<ArchitectureRule> parseRuleFile(Path docPath) throws IOException {
        LOG.info("Parsing the rule file ...");
        AsciiDocArc42Parser parser = new AsciiDocArc42Parser(gatherOWLNamespaces());
        LOG.debug("Parsing the architecture rules ...");
        List<ArchitectureRule> rules =
                parser.parseRulesFromDocumentation(docPath, TEMPORARY_DIRECTORY);
        LOG.debug("Parsing the mapping rules ...");
        parser.parseMappingRulesFromDocumentation(docPath, MAPPING_FILE_PATH);

        return rules;
    }

    private HashMap<String, String> gatherOWLNamespaces() {
        HashMap<String, String> supportedOWLNamespaces = new HashMap<>();
        for (var transformer : transformers) {
            supportedOWLNamespaces.putAll(transformer.getProvidedNamespaces());
        }
        supportedOWLNamespaces.putAll(check.getProvidedNamespaces());
        supportedOWLNamespaces.put(
                "architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");
        return supportedOWLNamespaces;
    }
}
