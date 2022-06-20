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

    @Before
    public void setUp() throws Exception {}

    @Test
    public void
            givenArchitectureRuleAndViolation_whenCreatingCheckedRuleWithIt_thenCheckedRuleContainsThese() {
        // given
        Model emptyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        ArchitectureRule rule =
                new ArchitectureRule(
                        0, "Only A can use B.", RuleType.DOMAIN_RANGE, emptyModel, null, null);

        ConstraintViolationBuilder violationBuilder = new ConstraintViolationBuilder();
        violationBuilder.addViolation("subject", "predicate", "object");
        List<ConstraintViolation> violations = new ArrayList<>();
        violations.add(violationBuilder.build());

        // when
        CheckedRule r = new CheckedRule(rule, violations);

        // then
        assertEquals(r.getRule(), rule);
        assertEquals(r.getViolations(), violations);
    }
}
