package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Test;

public class ModifierTest {

    @Test
    public void testModifierTransformation() throws FileNotFoundException {
        FamixOntologyNew ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));

        Modifier mod = new Modifier("public");

        Individual clazz =
                ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual("Test");
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);

        mod.modelIn(ontology, clazz);

        assertTrue(ontology.codeModel().containsLiteral(clazz, hasModifier, "public"));
    }
}
