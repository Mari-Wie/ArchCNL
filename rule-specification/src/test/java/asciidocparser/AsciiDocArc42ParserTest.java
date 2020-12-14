package asciidocparser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;

import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;
import datatypes.RuleType;

public class AsciiDocArc42ParserTest {

	@Test
	public void testCoarse() throws IOException {
		Map<String, String> namespaces = new HashMap<>();
		
		namespaces.put("architectureconformance", "http://arch-ont.org/ontologies/architectureconformance#");
		namespaces.put("famix", "http://arch-ont.org/ontologies/famix.owl#");
		namespaces.put("architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");
		AsciiDocArc42Parser parser = new AsciiDocArc42Parser(namespaces);
		
		parser.parseRulesFromDocumentation("./src/test/resources/rules.adoc", "./src/test/resources");
        parser.parseMappingRulesFromDocumentation("./src/test/resources/rules.adoc", "./src/test/resources/mapping.txt");
        
        ArchitectureRules ar = ArchitectureRules.getInstance();
        
        // assert that the extracted rules are correct
        assertEquals(2, ar.getRules().size());
        
        ArchitectureRule r0 = ar.getRuleWithID(0);
        ArchitectureRule r1 = ar.getRuleWithID(1);
        
        assertNotNull(r0);
        assertNotNull(r1);
        
        assertEquals(r0.getType(), RuleType.DOMAIN_RANGE);
        assertEquals(r1.getType(), RuleType.NEGATION);
        
        assertEquals(r0.getId().intValue(), 0);
        assertEquals(r1.getId().intValue(), 1);
        
        assertEquals(r0.getCnlSentence(), "Only LayerOne can use LayerTwo.");
        assertEquals(r1.getCnlSentence(), "No LayerTwo can use LayerOne.");
        
        assertEquals(r0.getContraintFile(), "./src/test/resources/architecture0.owl");
        assertEquals(r1.getContraintFile(), "./src/test/resources/architecture1.owl");
        
        // assert that the 1st rule's constraint file is correct
        Model expected0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected0.read("./src/test/resources/architecture0-expected.owl");
		
		Model actual0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual0.read("./src/test/resources/architecture0.owl");
		
		// 2nd rule
		Model expected1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected1.read("./src/test/resources/architecture1-expected.owl");
		
		Model actual1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual1.read("./src/test/resources/architecture1.owl");
		
		assertTrue(expected1.isIsomorphicWith(actual1));
        
        // assert that the mapping.txt is correct
        assertTrue(FileUtils.contentEqualsIgnoreEOL(
        		new File("./src/test/resources/mapping.txt"), 
        		new File("./src/test/resources/mapping-expected.txt"), "utf-8"));
	}

}
