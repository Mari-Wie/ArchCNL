package api;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

public class ExecuteMappingAPIImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCoarse() throws FileNotFoundException {
		final String outputPath = "src/test/resources/mapped.owl";
		List<String> ruleOntology = new ArrayList<>();
		ruleOntology.add("architecture0.owl");
		ruleOntology.add("architecture1.owl");
		ReasoningConfiguration config = ReasoningConfiguration.build();
		config.withMappingRules("mapping.txt");
		config.addPathsToConcepts(ruleOntology);
		config.withData("results.owl");
		
		ExecuteMappingAPI e = new ExecuteMappingAPIImpl();
		e.setReasoningConfiguration(config, outputPath);
		try {
			e.executeMapping();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		
		assertEquals(outputPath, e.getReasoningResultPath());
		
		Model expected = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		expected.read("mapped-expected.owl");
		
		Model actual = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		actual.read(outputPath);
		
		assertTrue(expected.isIsomorphicWith(actual));
	}

}
