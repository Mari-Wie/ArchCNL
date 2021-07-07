package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

public class AsciiDocCNLSentenceExtractorTest {

    private AsciiDocCNLSentenceExtractor extractor;

    @Before
    public void setUp() {
        extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/rules.adoc"));
    }

    @Test
    public void givenAsciiDocCNLSentenceExtractor_whenExtractArchitectureRules_thenArchitectureRulesExtracted() {
        //given, when, then
    	assertThat(
                extractor.extractArchitectureRules(),
                CoreMatchers.hasItems(
                        "Only LayerOne can use LayerTwo.", "No LayerTwo can use LayerOne."));
    }

    @Test
    public void givenAsciiDocCNLSentenceExtractor_whenExtractMappingRules_thenMappingRulesExtracted() {
    	//given, when, then
    	assertThat(
                extractor.extractMappingRules(),
                CoreMatchers.hasItems(
                        "useMapping: (?class rdf:type famix:FamixClass) (?class2 rdf:type famix:FamixClass) (?f rdf:type famix:Attribute) (?class famix:definesAttribute ?f) (?f famix:hasDeclaredType ?class2) -> (?class architecture:use ?class2)",
                        "isLayerOne: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerOne') -> (?class rdf:type architecture:LayerOne)",
                        "isLayerTwo: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerTwo') -> (?class rdf:type architecture:LayerTwo)"));
    }
}
