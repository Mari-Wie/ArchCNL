package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class CNLTranslatorTest {

    @Test
    public void testCNLTranslation() {
        CNLTranslator translator = new CNLTranslator();

        List<String> cnlSentences =
                Arrays.asList("Only LayerOne can use LayerTwo.", "No LayerTwo can use LayerOne.");
        List<ArchitectureRule> rules = translator.translate(cnlSentences, "./src/test/resources");

        assertThat(
                rules,
                CoreMatchers.hasItems(
                        new ArchitectureRule(
                                0,
                                "Only LayerOne can use LayerTwo.",
                                RuleType.DOMAIN_RANGE,
                                "./src/test/resources/architecture0.owl"),
                        new ArchitectureRule(
                                1,
                                "No LayerTwo can use LayerOne.",
                                RuleType.NEGATION,
                                "./src/test/resources/architecture1.owl")));

        // assert that the constraint files are correct:

        // 1st one
        Model expected0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        expected0.read("./src/test/resources/architecture0-expected.owl");

        Model actual0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        actual0.read("./src/test/resources/architecture0.owl");

        assertTrue(expected0.isIsomorphicWith(actual0));

        // 2nd one
        Model expected1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        expected1.read("./src/test/resources/architecture1-expected.owl");

        Model actual1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        actual1.read("./src/test/resources/architecture1.owl");

        assertTrue(expected1.isIsomorphicWith(actual1));
    }
}
