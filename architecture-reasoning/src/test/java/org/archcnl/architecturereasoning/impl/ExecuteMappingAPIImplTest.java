package org.archcnl.architecturereasoning.impl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.architecturereasoning.api.ExecuteMappingAPI;
import org.archcnl.architecturereasoning.api.ReasoningConfiguration;
import org.archcnl.architecturereasoning.impl.ExecuteMappingAPIImpl;
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
		ReasoningConfiguration config = ReasoningConfiguration.builder()
				.withMappingRules("mapping.txt")
				.withPathsToConcepts(ruleOntology)
				.withData("results.owl")
				.withResult(outputPath)
				.build();

		ExecuteMappingAPI e = new ExecuteMappingAPIImpl();
		e.setReasoningConfiguration(config);
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
