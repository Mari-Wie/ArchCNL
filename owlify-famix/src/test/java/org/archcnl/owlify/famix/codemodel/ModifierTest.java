package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Test;

public class ModifierTest {

    @Test
    public void testModifierTransformation() throws FileNotFoundException {
        FamixOntology ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));

        Modifier mod = new Modifier("public");
        Individual clazz = ontology.createIndividual(FamixClass, "Test");

        mod.modelIn(ontology, clazz);

        assertTrue(
                ontology.codeModel().containsLiteral(clazz, ontology.get(hasModifier), "public"));
    }
}
