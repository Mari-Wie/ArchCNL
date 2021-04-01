package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ArchitectureToCodeMappingTest {

    private static final String prefix = "@prefix test: <http://example.org/ontologies/test#>\n";
    private static final String rule1 =
            "foobar: (?class rdf:type test:Foo) (?class2 rdf:type test:Bar) -> (?class test:qux ?class2)";
    private static final String rule2 =
            "abc: (?a test:qux test:A) (?a ?b test:B) (?c test:qux test:C)  -> (?a ?b ?c)";
    private static final String expectation = prefix + "[" + rule1 + "]\n[" + rule2 + "]\n";

    private ArchitectureToCodeMapping mapping;

    @Before
    public void setUp() {
        mapping = new ArchitectureToCodeMapping(prefix, Arrays.asList(rule1, rule2));
    }

    @Test
    public void testAsSWRL() throws IOException {
        assertEquals(expectation, mapping.asSWRL());
    }

    @Test
    public void testWrite() throws IOException {
        StringWriter writer = new StringWriter();
        mapping.write(writer);

        assertEquals(expectation, writer.toString());
    }
}
