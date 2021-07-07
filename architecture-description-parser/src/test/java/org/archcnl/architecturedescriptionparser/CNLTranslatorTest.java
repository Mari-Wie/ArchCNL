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
    public void givenCNLSentences_whenTranslateToArchitectureRules_thenRulesCorrectlyTranslated() {
        //given
    	CNLTranslator translator = new CNLTranslator();
        List<String> cnlSentences = Arrays.asList("Only LayerOne can use LayerTwo.", "No LayerTwo can use LayerOne.");
        
        //when
        List<ArchitectureRule> rules = translator.translate(cnlSentences, "./src/test/resources");

        //then
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
    }

        @Test
        public void givenConstraintFile_whenReadByOntologyModel_thenExpectedModelIsIsomorphicWithActualModel() {
        // assert that the 1. constraint files is correct:
        //given
        Model expected0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        Model actual0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        
        //when
        expected0.read("./src/test/resources/architecture0-expected.owl");       
        actual0.read("./src/test/resources/architecture0.owl");

        //then
        assertTrue(expected0.isIsomorphicWith(actual0));

        // assert that the 2. constraint files is correct:
        //given
        Model expected1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        Model actual1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        
        //when
        expected1.read("./src/test/resources/architecture1-expected.owl");        
        actual1.read("./src/test/resources/architecture1.owl");

        //then
        assertTrue(expected1.isIsomorphicWith(actual1));
    }
}
