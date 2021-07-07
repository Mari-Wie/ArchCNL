package org.archcnl.architecturedescriptionparser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArchitectureToCodeMappingTest {

    private static final String prefix = "@prefix test: <http://example.org/ontologies/test#>\n";
    private static final String rule1 =
            "foobar: (?class rdf:type test:Foo) (?class2 rdf:type test:Bar) -> (?class test:qux ?class2)";
    private static final String rule2 =
            "abc: (?a test:qux test:A) (?a ?b test:B) (?c test:qux test:C)  -> (?a ?b ?c)";
    private static final String expectation = prefix + "[" + rule1 + "]\n[" + rule2 + "]\n"; 
    private ArchitectureToCodeMapping mapping;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setUp() {
    	mapping = new ArchitectureToCodeMapping(prefix, Arrays.asList(rule1, rule2));
    }

    @Test
    public void givenArchitectureToCodeMapping_whenReturnedAsSWRL_thenSWRLEqualsExpectation() throws IOException {
    	//given, when, then
        assertEquals(expectation, mapping.asSWRL());
    }

    @Test
    public void givenArchitectureToCodeMapping_whenWrittenToAStringWriter_thenStringWriterToStringEqualsExpectation() throws IOException {
    	//given    	
    	StringWriter writer = new StringWriter();
        
    	//when
    	mapping.write(writer);

    	//then
        assertEquals(expectation, writer.toString());
    }
}
