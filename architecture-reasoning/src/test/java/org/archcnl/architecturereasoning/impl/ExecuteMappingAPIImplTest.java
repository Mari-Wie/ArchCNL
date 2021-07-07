package org.archcnl.architecturereasoning.impl;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.architecturereasoning.api.ExecuteMappingAPI;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExecuteMappingAPIImplTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void givenArchitectureModel_whenExecuteMapping_thenActualModelIsIsomorphicWithExpectedModel() throws IOException {
		// given
		List<ArchitectureRule> architectureModel = Arrays.asList(
				new ArchitectureRule(0, "Only LayerOne can use LayerTwo.", RuleType.DOMAIN_RANGE,
						"./src/test/resources/architecture0.owl"),
				new ArchitectureRule(1, "No LayerTwo can use LayerOne.", RuleType.NEGATION,
						"./src/test/resources/architecture1.owl"));
		
		ExecuteMappingAPI e = new ExecuteMappingAPIImpl();		
		Model codeModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		Model expected = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		
		//when
		codeModel.read("src/test/resources/results.owl");
		expected.read("mapped-expected.owl");
		Model actual = e.executeMapping(codeModel, architectureModel, "src/test/resources/mapping.txt");

		// then
		assertTrue(expected.isIsomorphicWith(actual));
	}
}
