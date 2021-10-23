package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class TypeTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testPrimitiveType() {
        Type type = new Type("int", "int", true);
        Individual individual = type.getIndividual(ontology);

        assertEquals(FamixOntology.PREFIX + "int", individual.getURI());
    }

    @Test
    public void testPrimitiveTypesHaveSameIndividual() {
        Type type1 = new Type("int", "int", true);
        Type type2 = new Type("int", "int", true);

        assertSame(type1.getIndividual(ontology), type2.getIndividual(ontology));
    }

    @Test
    public void testUnknownType() {
        final String simpleName = "Type";
        final String name = "namespace." + simpleName;
        Type type = new Type(name, simpleName, false);
        Individual individual = type.getIndividual(ontology);

        assertEquals(FamixClass.individualUri(name), individual.getURI());
        assertTrue(
                ontology.codeModel().containsLiteral(individual, ontology.get(isExternal), true));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasName), simpleName));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasFullQualifiedName), name));
        assertEquals(FamixClass.uri(), individual.getOntClass().getURI());
        assertTrue(ontology.typeCache().isDefined(name));
    }

    @Test
    public void testKnownType() {
        final String name = "namespace.Type";
        Individual individual = ontology.createIndividual(FamixClass, name);
        Type type = new Type(name, "Type", false);

        ontology.typeCache().addDefinedType(name, individual);
        assertSame(individual, type.getIndividual(ontology));
    }
}
