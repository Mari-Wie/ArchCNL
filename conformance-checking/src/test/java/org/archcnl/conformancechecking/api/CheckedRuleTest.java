package org.archcnl.conformancechecking.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.ConstraintViolation;
import org.archcnl.common.datatypes.ConstraintViolation.ConstraintViolationBuilder;
import org.archcnl.common.datatypes.RuleType;
import org.junit.Before;
import org.junit.Test;

public class CheckedRuleTest {
	private CheckedRule r;
	private ArchitectureRule rule;
	private List<ConstraintViolation> violations;

	@Before
	public void setup() {
		// given
		Model emptyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		rule = new ArchitectureRule(0, "Only A can use B.", RuleType.DOMAIN_RANGE, emptyModel);

		ConstraintViolationBuilder violationBuilder = new ConstraintViolationBuilder();
		violationBuilder.addViolation("subject", "predicate", "object");

		violations = new ArrayList<>();
		violations.add(violationBuilder.build());
		
		// when
		r = new CheckedRule(rule, violations);
	}

	@Test
	public void givenArchitectureRule_whenNewCheckedRuleInstantiated_thenCheckedRuleRuleEqualsArchitectureRule() {
		// then
		assertEquals(r.getRule(), rule);
	}

	@Test
	public void givenConstraintViolations_whenNewCheckedRuleInstantiated_thenCheckedRuleViolationsEqualsConstraintViolation() {
		// then
		assertEquals(r.getViolations(), violations);
	}
}
