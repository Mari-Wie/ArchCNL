package impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.architecture.cnl.ide.CNL2OWLGenerator;
import org.architecture.cnl.mapping.ide.MappingDSL2OWLGenerator;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;

import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;

public class AsciiDocArc42Parser {

	private final String TMP_ARCHITECTURE_FILE_NAME = "./tmp.architecture";
	private final static String TMP_MAPPING_FILE_NAME = "./tmp.mapping";
	private final static String EXTENSION = ".architecture";
	private final static String OWL_EXTENSION = ".owl";
	private final static String PREFIX = "tmp";

	private static int id = 0;

	public static void parseRulesFromDocumentation(String path) {

		Asciidoctor ascii = Asciidoctor.Factory.create();
		CNL2OWLGenerator generator = new CNL2OWLGenerator();

		Document doc = ascii.loadFile(new File(path), new HashMap<String, Object>());
		Map<Object, Object> selector = new HashMap<Object, Object>();
		selector.put("role", ":rule");

		List<AbstractBlock> result = doc.findBy(selector);
		int id_for_file = 0;
		for (AbstractBlock abstractBlock : result) {
			Block b = (Block) abstractBlock;
			List<String> lines = b.lines();
			for (String line : lines) {
				File f = new File(PREFIX + "_" + id_for_file + EXTENSION);

				ArchitectureRule rule = new ArchitectureRule();
				rule.setId(id);
				rule.setCnlSentence(line);
				ArchitectureRules rules = ArchitectureRules.getInstance();
				rules.addRule(rule, id);
				id++;
				try {
					FileUtils.writeStringToFile(f, line + "\n", (Charset) null, true);
					rules.addRuleWithPathToConstraint(rule, id_for_file,
							"./architecture" + id_for_file + OWL_EXTENSION);
					generator.transformCNLFile(PREFIX + "_" + id_for_file + EXTENSION);
					System.out.println("transformed");

					f.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			id_for_file++;
		}
	}

	public static void parseMappingRulesFromDocumentation(String path) {
		Asciidoctor ascii = Asciidoctor.Factory.create();
		MappingDSL2OWLGenerator generator = new MappingDSL2OWLGenerator();
		Document doc = ascii.loadFile(new File(path), new HashMap<String, Object>());
		Map<Object, Object> selector = new HashMap<Object, Object>();
		selector.put("role", ":mapping");

		List<AbstractBlock> result = doc.findBy(selector);
		File f = new File(TMP_MAPPING_FILE_NAME);
		for (AbstractBlock abstractBlock : result) {
			
			Block b = (Block) abstractBlock;
			List<String> lines = b.lines();
			for (String line : lines) {
				try {
					FileUtils.writeStringToFile(f, line+"\n",(Charset)null, true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		generator.transformMapping(TMP_MAPPING_FILE_NAME);
		f.delete();

	}

	public static void main(String[] args) {
		String path = "C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl\\conformance-checking\\arc42-building-block-view.adoc";
		parseRulesFromDocumentation(path);
		parseMappingRulesFromDocumentation(path);
	}

}
