package org.archcnl.architecturedescriptionparser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ArchitectureRule;

/** Parser for AsciiDoc files containing architecture rules and mapping rules. */
public class AsciiDocArc42Parser {
    private static final Logger LOG = LogManager.getLogger(AsciiDocArc42Parser.class);

    private final Map<String, String> ONTOLOGY_PREFIXES_FOR_MAPPING;

    /**
     * Constructor.
     *
     * @param ontologyNamespaces A map that maps abbreviation of OWL namespaces to the full URI of
     *     the namespace. The map defines which abbreviations can be used in the processes .adoc
     *     files.
     */
    public AsciiDocArc42Parser(Map<String, String> ontologyNamespaces) {
        ONTOLOGY_PREFIXES_FOR_MAPPING = ontologyNamespaces;
    }

    /**
     * Search .adoc-file for keywords "rule" and "mapping" Split to single lines = single rules
     *
     * <p>For every rule (=line): - Create one RuleFile (tmp_{id}.architecture) - Add Rule to
     * common.ArchitectureRules - Transform RuleFile to OntologyFile (./architecture{id}.owl) -
     * Delete RuleFile
     *
     * @param path - Path of Rule-File (.adoc-File)
     * @param outputDirectory - Path where the generated files will be placed (without a slash (/)
     *     at the end)
     * @return The list of architecture rules from the rule file.
     */
    public List<ArchitectureRule> parseRulesFromDocumentation(Path path, String outputDirectory) {
        LOG.trace("Starting parseRulesFromDocumentation ...");
        LOG.debug("Parsing architecture rules from file: " + path);

        List<ArchitectureRule> extractedRules =
                new AsciiDocCNLSentenceExtractor(path).extractArchitectureRules();
        List<ArchitectureRule> translatedRules =
                new CNLTranslator().translate(extractedRules, outputDirectory);

        return translatedRules;
    }

    /**
     * Extracts architecture-to-code mapping rules from the given .adoc file.
     *
     * @param path - Path of Rule-File (.adoc-File)
     * @param outputFile - Path where the resulting ontology file (.owl) will be stored.
     * @throws IOException When accessing a file fails
     */
    public void parseMappingRulesFromDocumentation(Path path, String outputFile)
            throws IOException {
        LOG.trace("Starting parseMappingRulesFromDocumentation ...");

        LOG.debug("Parsing mapping rules from file: " + path);

        List<String> lines = new AsciiDocCNLSentenceExtractor(path).extractMappingRules();
        ArchitectureToCodeMapping mapping =
                new ArchitectureToCodeMapping(ONTOLOGY_PREFIXES_FOR_MAPPING, lines);

        LOG.debug("Writing mapping rules to seperate file: " + outputFile);

        try (FileWriter writer = new FileWriter(outputFile, false)) {
            mapping.write(writer);
        } catch (IOException e) {
            LOG.fatal("Unable to write mapping rules to file: " + outputFile);
            throw e;
        }

        LOG.info("Processed architecture-to-code mapping");
    }
}
