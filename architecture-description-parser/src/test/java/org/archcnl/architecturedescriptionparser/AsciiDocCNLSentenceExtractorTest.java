package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class AsciiDocCNLSentenceExtractorTest {

  private AsciiDocCNLSentenceExtractor extractor;

  @Test
  public void givenArchitectureRules_whenGivenToSentenceExtractor_thenRulesAreExtractedCorrectly() {
    // given, when
    extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/rules.adoc"));
    // then
    assertThat(extractor.extractArchitectureRules(),
        CoreMatchers.hasItems("Only LayerOne can use LayerTwo.", "No LayerTwo can use LayerOne."));
  }

  @Test
  public void givenMappingRules_whenGivenToSentenceExtractor_thenMappingsAreExtractedCorrectly() {
    // given, when
    extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/rules.adoc"));
    // then
    assertThat(extractor.extractMappingRules(), CoreMatchers.hasItems(
        "useMapping: (?class rdf:type famix:FamixClass) (?class2 rdf:type famix:FamixClass) (?f rdf:type famix:Attribute) (?class famix:definesAttribute ?f) (?f famix:hasDeclaredType ?class2) -> (?class architecture:use ?class2)",
        "isLayerOne: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerOne') -> (?class rdf:type architecture:LayerOne)",
        "isLayerTwo: (?class rdf:type famix:FamixClass) (?class famix:hasName ?name) regex(?name, 'TestLayerTwo') -> (?class rdf:type architecture:LayerTwo)"));
  }

  @Test
  public void givenEmptyArchitectureRules_whenGivenToSentenceExtractor_thenExtractedRulesIsEmpty() {
    // given, when
    extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/emptyrules.adoc"));
    // then
    assertTrue(extractor.extractArchitectureRules().isEmpty());
  }

  @Test
  public void givenEmptyMappingsRules_whenGivenToSentenceExtractor_thenExtractedRulesIsEmpty() {
    // given, when
    extractor = new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/emptyrules.adoc"));
    // then
    assertTrue(extractor.extractMappingRules().isEmpty());
  }

  @Test
  public void givenNonAsciidocFile_whenGivenToSentenceExtractor_thenExtractorIsEmpty() {
    // given, when
    extractor =
        new AsciiDocCNLSentenceExtractor(Paths.get("./src/test/resources/mapping-expected.txt"));
    // then
    assertTrue(extractor.extractArchitectureRules().isEmpty());
    assertTrue(extractor.extractMappingRules().isEmpty());
  }
}
