package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class TypeTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testPrimitiveType() {
        Type type = new Type("int", true);
        Individual individual = type.getIndividual(ontology);

        assertEquals(FamixURIs.PREFIX + "int", individual.getURI());
    }

    @Test
    public void testPrimitiveTypesHaveSameIndividual() {
        Type type1 = new Type("int", true);
        Type type2 = new Type("int", true);

        assertSame(type1.getIndividual(ontology), type2.getIndividual(ontology));
    }

    @Test
    public void testUnknownType() {
        final String name = "namespace.Type";
        Type type = new Type(name, false);
        Individual individual = type.getIndividual(ontology);
        Property isExternal = ontology.codeModel().getProperty(FamixURIs.IS_EXTERNAL);
        Property hasFullQualifiedName =
                ontology.codeModel().getProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME);

        assertEquals(name, individual.getURI());
        assertTrue(ontology.codeModel().containsLiteral(individual, isExternal, true));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasFullQualifiedName, name));
        assertEquals(FamixURIs.FAMIX_CLASS, individual.getOntClass().getURI());
        assertTrue(ontology.typeCache().isDefined(name));
    }

    @Test
    public void testKnownType() {
        final String name = "namespace.Type";
        Individual individual =
                ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual(name);
        Type type = new Type(name, false);

        ontology.typeCache().addDefinedType(name, individual);
        assertSame(individual, type.getIndividual(ontology));
    }
}
