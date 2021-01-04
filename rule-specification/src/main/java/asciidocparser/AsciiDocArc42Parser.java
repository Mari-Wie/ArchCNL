package asciidocparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.architecture.cnl.CNL2OWLGenerator;

import datatypes.ArchitectureRule;
import datatypes.RuleType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Parser for AsciiDoc files containing architecture rules and mapping rules.
 */
public class AsciiDocArc42Parser {
	private static final Logger LOG = LogManager.getLogger(AsciiDocArc42Parser.class);

	private final static String EXTENSION = ".architecture";
	private final static String OWL_EXTENSION = ".owl";
	private final static String PREFIX = "tmp";

	private final String ONTOLOGY_PREFIXES_FOR_MAPPING;

	private final String ARCHITECTURE_RULE_TAG = "rule";
	private final String MAPPING_RULE_TAG = "mapping";

	private List<String> ontologyPaths;

	/**
	 * Constructor.
	 * 
	 * @param ontologyNamespaces A map that maps abbreviation of OWL namespaces to
	 *                           the full URI of the namespace. The map defines
	 *                           which abbreviations can be used in the processes
	 *                           .adoc files.
	 */
	public AsciiDocArc42Parser(Map<String, String> ontologyNamespaces) {
		ONTOLOGY_PREFIXES_FOR_MAPPING = generatePrefix(ontologyNamespaces);
		ontologyPaths = new ArrayList<String>();
	}

	private String generatePrefix(Map<String, String> ontologyNamespaces) {
		StringBuilder builder = new StringBuilder();

		for (String abbreviation : ontologyNamespaces.keySet()) {
			builder.append("@prefix ");
			builder.append(abbreviation);
			builder.append(": <");
			builder.append(ontologyNamespaces.get(abbreviation));
			builder.append(">\n");
		}

		String res = builder.toString();
		return res;
	}

	/**
	 * Search .adoc-file for keywords "rule" and "mapping" Split to single lines =
	 * single rules
	 * 
	 * For every rule (=line): - Create one RuleFile (tmp_{id}.architecture) - Add
	 * Rule to common.ArchitectureRules - Transform RuleFile to OntologyFile
	 * (./architecture{id}.owl) - Delete RuleFile
	 * 
	 * @param path            - Path of Rule-File (.adoc-File)
	 * @param outputDirectory - Path where the generated files will be placed
	 *                        (without a slash (/) at the end)
	 * @return The list of architecture rules from the rule file.
	 */
	public List<ArchitectureRule> parseRulesFromDocumentation(String path, String outputDirectory) {
		LOG.trace("Starting parseRulesFromDocumentation ...");
		LOG.debug("Parsing architecture rules from file: " + path);

		List<StructuralNode> result = parseAsciidocFile(path, ARCHITECTURE_RULE_TAG);

		CNL2OWLGenerator generator = new CNL2OWLGenerator();
		List<ArchitectureRule> rules = new ArrayList<>();

		int id_for_file = 0;
		for (StructuralNode structuralNode : result) {
			Block b = (Block) structuralNode;
			List<String> lines = b.getLines();

			for (String line : lines) {
				parseLine(line, id_for_file, generator, rules, outputDirectory);
				id_for_file++;
			}
		}

		return rules;
	}

	private void parseLine(String line, int id_for_file, CNL2OWLGenerator generator, List<ArchitectureRule> rules,
			String outputDirectory) {
		LOG.debug("Found an architecture rule:");
		String ontologyFile = "/architecture" + id_for_file + OWL_EXTENSION;
		String rulePath = PREFIX + "_" + id_for_file + EXTENSION;
		String ontologyPath = outputDirectory + ontologyFile;
		LOG.debug("Rule Id      : " + id_for_file);
		LOG.debug("Rule         : " + line);
		LOG.debug("File Id      : " + id_for_file);
		LOG.debug("RulePath     : " + rulePath);
		LOG.debug("OntologyPath : " + ontologyPath);

		File f = new File(rulePath);
		try {
			// TODO: Passing the rule to the generator as a string rather than as a file
			// would increase performance.
			LOG.debug("Writing the rule to a seperate file: " + rulePath);
			ontologyPaths.add(ontologyPath);

			FileUtils.writeStringToFile(f, line + "\n", (Charset) null, true);

			LOG.debug("Transforming the rule from CNL to an OWL constraint ...");
			RuleType typeOfParsedRule = generator.transformCNLFile(rulePath, ontologyPath);

			if (typeOfParsedRule == null) {
				LOG.error("The parser could not parse the following rule: " + line);
			} else {
				rules.add(new ArchitectureRule(id_for_file, line, typeOfParsedRule, ontologyPath));
				LOG.info("Added the architecture rule: " + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("IOException while processing " + rulePath + ": " + e.toString());
		} finally { // Relicts of tmp_{id}.architecture create ExecutionErrors due to
					// NullPointerExceptions in transform CNLFile.
			if (f.exists()) {
				f.delete();
			}
		}
	}

	private List<StructuralNode> parseAsciidocFile(String path, String tag) {
		Asciidoctor ascii = Asciidoctor.Factory.create();
		File file = new File(path);
		Document doc = ascii.loadFile(file, new HashMap<String, Object>());
		Map<Object, Object> selector = new HashMap<Object, Object>();
		selector.put("role", tag);
		List<StructuralNode> result = doc.findBy(selector);
		return result;
	}

	/**
	 * Extracts architecture-to-code mapping rules from the given .adoc file.
	 * 
	 * @param path       - Path of Rule-File (.adoc-File)
	 * @param outputFile - Path where the resulting ontology file (.owl) will be
	 *                   stored.
	 */
	public void parseMappingRulesFromDocumentation(String path, String outputFile) {
		LOG.trace("Starting parseMappingRulesFromDocumentation ...");

		LOG.debug("Parsing mapping rules from file: " + path);
		List<StructuralNode> result = parseAsciidocFile(path, MAPPING_RULE_TAG);
		String allMappingRules = "";
		for (StructuralNode structuralNode : result) {

			String tmp = "";
			Block b = (Block) structuralNode;
			List<String> lines = b.getLines();
			for (String line : lines) {
				tmp += line;
			}
			LOG.debug("Encountered mapping rule: [" + tmp + "]");
			allMappingRules += "[" + tmp + "]" + "\n";

		}
		writeMappingRulesToFile(outputFile, allMappingRules);
		LOG.info("Processed architecture-to-code mapping");
	}

	void writeMappingRulesToFile(String outputFile, String allMappingRules) {
		LOG.debug("Writing mapping rules to seperate file: " + outputFile);
		File f = createNewFile(outputFile);
		try {
			FileUtils.writeStringToFile(f, ONTOLOGY_PREFIXES_FOR_MAPPING + allMappingRules, (Charset) null, true);
		} catch (IOException e) {
			LOG.fatal("Unable to write mapping rules to file: " + outputFile);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	File createNewFile(String outputFile) {
		File f = new File(outputFile);
		if (f.exists()) {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
				LOG.error("Cannot create file: " + f.getAbsolutePath());
			}
		}
		return f;
	}
}
