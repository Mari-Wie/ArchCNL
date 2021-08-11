package org.archcnl.common.datatypes;

import static org.junit.Assert.*;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureRuleTest {

    @Before
    public void setUp() throws Exception {}

    @Test
    public void
            givenArchitectureRules_whenCompared_thenIdenticalRulesAreEqualAndHaveSameHashCode() {
        // given, when
        Model model1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        Model model2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        Model model3 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

        model1.createProperty("prop");
        model2.createProperty("prop");

        ArchitectureRule r1 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, model1);
        ArchitectureRule r2 = new ArchitectureRule(0, "test", RuleType.AT_LEAST, model2);
        ArchitectureRule r3 = new ArchitectureRule(1, "not test", RuleType.AT_MOST, model3);

        // then
        assertEquals(r1, r1);
        assertEquals(r1, r2);
        assertNotEquals(r1, null);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}
