package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.common.datatypes.ArchitectureRule;
import org.archcnl.common.datatypes.RuleType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class AsciiDocArc42ParserTest {

    @Test
    public void givenArchitectureRules_whenParsedByAsciiDocParser_thenRulesExtractedCorrectly()
            throws IOException {
        // given
        Map<String, String> namespaces = new HashMap<>();

        namespaces.put(
                "architectureconformance",
                "http://arch-ont.org/ontologies/architectureconformance#");
        namespaces.put("famix", "http://arch-ont.org/ontologies/famix.owl#");
        namespaces.put("architecture", "http://www.arch-ont.org/ontologies/architecture.owl#");
        AsciiDocArc42Parser parser = new AsciiDocArc42Parser(namespaces);

        Path testRulesPath = Paths.get("./src/test/resources/rules.adoc");
        // when
        List<ArchitectureRule> rules =
                parser.parseRulesFromDocumentation(testRulesPath, "./src/test/resources");
        parser.parseMappingRulesFromDocumentation(
                testRulesPath, "./src/test/resources/mapping.txt");

        // then (assert that the extracted rules are correct)
        assertEquals(2, rules.size());

        assertThat(
                rules,
                CoreMatchers.hasItems(
                        ArchitectureRule.createArchRuleForTests(
                                0,
                                "Only LayerOne can use LayerTwo.",
                                RuleType.DOMAIN_RANGE,
                                "./src/test/resources/architecture0.owl",
                                null,
                                null),
                        ArchitectureRule.createArchRuleForTests(
                                1,
                                "No LayerTwo can use LayerOne.",
                                RuleType.NEGATION,
                                "./src/test/resources/architecture1.owl",
                                LocalDate.of(1940, 01, 01),
                                LocalDate.of(2100, 01, 01))));

        // assert that the 1st rule's constraint file is correct
        // given, when
        Model expected0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        expected0.read("./src/test/resources/architecture0-expected.owl");

        Model actual0 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        actual0.read("./src/test/resources/architecture0.owl");

        // then
        assertTrue(expected0.isIsomorphicWith(actual0));

        // assert that the 2nd rule's constraint file is correct
        // given, when
        Model expected1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        expected1.read("./src/test/resources/architecture1-expected.owl");

        Model actual1 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        actual1.read("./src/test/resources/architecture1.owl");

        // then
        assertTrue(expected1.isIsomorphicWith(actual1));

        // assert that the mapping.txt is correct
        assertTrue(
                FileUtils.contentEqualsIgnoreEOL(
                        new File("./src/test/resources/mapping.txt"),
                        new File("./src/test/resources/mapping-expected.txt"),
                        "utf-8"));
    }
}
