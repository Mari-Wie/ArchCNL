package org.archcnl.common.datatypes;

import static org.junit.Assert.*;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureRuleTest {

	private Model model1, model2, model3;
	private ArchitectureRule r1, r2, r3;

	@Before
	public void setup() {
		model1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model3 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

		model1.createProperty("prop");
		model2.createProperty("prop");

		r1 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, model1);
		r2 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, model2);
		r3 = new ArchitectureRule(1, "not test", RuleType.AT_MOST, model3);
	}

	@Test
	public void givenEqualArchitectureRules_whenInDifferentModels_thenRulesAreEqual() {
		// given, when, then
		assertEquals(r1, r1);
		assertEquals(r1, r2);
		assertEquals(r1.hashCode(), r2.hashCode());
	}

	@Test
	public void givenNotEqualArchitectureRules_whenInDifferentModels_thenRulesAreNotEqual() {
		// given, when, then
		assertNotEquals(r1, null);
		assertNotEquals(r1, r3);
	}
}
