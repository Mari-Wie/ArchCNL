package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class AsciiDocCNLSentenceExtractorTest {

    private AsciiDocCNLSentenceExtractor extractor;

    @Test
    public void testArchitectureRuleExtraction() {
        extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/rules.adoc"));
        assertThat(
                extractor.extractArchitectureRules(),
                CoreMatchers.hasItems(
                        "Only LayerOne can use LayerTwo.", "No LayerTwo can use LayerOne."));
    }

    @Test
    public void testMappingRuleExtraction() {
        extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/rules.adoc"));
        assertThat(
                extractor.extractMappingRules(),
                CoreMatchers.hasItems(
                        "useMapping: (?class rdf:type famix:FamixClass) (?class2 rdf:type famix:FamixClass) (?f rdf:type famix:Attribute) (?class famix:definesAttribute ?f) (?f famix:hasDeclaredType ?class2) -> (?class architecture:use ?class2)",
                        "isLayerOne: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerOne') -> (?class rdf:type architecture:LayerOne)",
                        "isLayerTwo: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerTwo') -> (?class rdf:type architecture:LayerTwo)"));
    }

    @Test
    public void testEmptyArchitectureRule() {
        extractor =
                new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/emptyrules.adoc"));
        assertTrue(extractor.extractArchitectureRules().isEmpty());
    }

    @Test
    public void testEmptyMappingRule() {
        extractor =
                new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/emptyrules.adoc"));
        assertTrue(extractor.extractMappingRules().isEmpty());
    }

    @Test
    public void testNoAsciidocFile() {
        extractor =
                new AsciiDocCNLSentenceExtractor(
                        Paths.get("./src/test/resources/mapping-expected.txt"));
        assertTrue(extractor.extractArchitectureRules().isEmpty());
        assertTrue(extractor.extractMappingRules().isEmpty());
    }
}
