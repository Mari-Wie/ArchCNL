package org.archcnl.owlify.famix.ontology;

import static org.junit.Assert.*;

import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Test;

public class FamixOntologyTest {

    private final FamixClasses clazz = FamixClasses.Attribute;
    private final String prefix = clazz.uri() + "/";
    
    @Test
    public void testNameGenerationSimple() {
        String actual = clazz.individualUri("Easy");
        String expected = prefix + "Easy";
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNameGenerationSpaces() {
        String actual = clazz.individualUri("The next string is pretty weird");
        String expected = prefix + "The-next-string-is-pretty-weird";
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNameGenerationReservedCharacters() {
        String actual = clazz.individualUri("Reserved!#$&'()*+,/:;=?@[]");
        String expected = prefix + "Reserved%21%23%24%26%27%28%29%2A%2B%2C%2F%3A%3B%3D%3F%40%5B%5D";
        
        assertEquals(expected, actual);
    }

}
