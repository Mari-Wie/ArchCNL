package org.archcnl.owlify.famix.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties;
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

    @Test
    public void testURIsMatchURIsFromOntologyFile() throws FileNotFoundException {
        OntModel baseOnt = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        baseOnt.read(new FileInputStream("src/main/resources/ontologies/famix.owl"), null);

        for (FamixClasses ontClass : FamixClasses.values()) {
            assertNotNull(
                    ontClass.name() + " from FamixClasses has no associated class in famix.owl.",
                    baseOnt.getOntClass(ontClass.uri()));
        }

        for (FamixObjectProperties prop : FamixObjectProperties.values()) {
            assertNotNull(
                    prop.name()
                            + " from FamixObjectProperties has no associated object property in famix.owl.",
                    baseOnt.getObjectProperty(prop.uri()));
        }

        for (FamixDatatypeProperties prop : FamixDatatypeProperties.values()) {
            assertNotNull(
                    prop.name()
                            + " from FamixDatatypeProperties has no associated datatype property in famix.owl.",
                    baseOnt.getDatatypeProperty(prop.uri()));
        }
    }
}
