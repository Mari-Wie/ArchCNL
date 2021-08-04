package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureToCodeMappingTest {

  private static final String testPrefix = "test";
  private static final String testUri = "http://example.org/ontologies/test#";
  private static final String prefix = "@prefix " + testPrefix + ": <" + testUri + ">\n";
  private static final String rule1 =
      "foobar: (?class rdf:type " + testPrefix + ":Foo) (?class2 rdf:type " + testPrefix
          + ":Bar) -> (?class " + testPrefix + ":qux ?class2)";
  private static final String rule2 =
      "abc: (?a test:qux test:A) (?a ?b test:B) (?c test:qux test:C)  -> (?a ?b ?c)";
  private static final String expectation = prefix + "[" + rule1 + "]\n[" + rule2 + "]\n";

  private ArchitectureToCodeMapping mapping;
  private Map<String, String> prefixMapping;

  @Before
  public void setUp() {
    prefixMapping = new HashMap<>();
    prefixMapping.put(testPrefix, testUri);
  }

  @Test
  public void givenArchitectureToCodeMapping_whenMappingIsReturnedAsSWRL_thenMappingEqualsExpectationString()
      throws IOException {
    // given, when
    mapping = new ArchitectureToCodeMapping(prefixMapping, Arrays.asList(rule1, rule2));
    // then
    assertEquals(expectation, mapping.asSWRL());
  }

  @Test
  public void givenEmptyArchitectureToCodeMapping_whenMappingIsReturned_thenMappingEqualsPrefix()
      throws IOException {
    // given, when
    StringWriter writer = new StringWriter();
    mapping = new ArchitectureToCodeMapping(prefixMapping, new ArrayList<String>());
    mapping.write(writer);
    // then
    assertEquals(prefix, mapping.asSWRL());
    assertEquals(prefix, writer.toString());
  }

  @Test
  public void givenArchitectureToCodeMapping_whenMappingIsWrittenToStringWriter_thenStringWriterToStringEqualsExpectationString()
      throws IOException {
    // given
    mapping = new ArchitectureToCodeMapping(prefixMapping, Arrays.asList(rule1, rule2));
    StringWriter writer = new StringWriter();
    // when
    mapping.write(writer);
    // then
    assertEquals(expectation, writer.toString());
  }
}
