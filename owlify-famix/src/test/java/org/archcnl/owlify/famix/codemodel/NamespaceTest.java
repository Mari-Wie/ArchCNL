package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class NamespaceTest {

    private FamixOntologyNew ontology;
    private static final String prefix = "NAMESPACE/";
    private static final String type1 = "namespace.SomeType";
    private static final String type2 = "namespace.SomeOtherType";

    private Individual type1Individual;
    private Individual type2Individual;
    private Property namespaceContains;
    private Property hasName;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));

        type1Individual =
                ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual(type1);
        type2Individual =
                ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual(type2);

        ontology.typeCache().addDefinedType(type1, type1Individual);
        ontology.typeCache().addDefinedType(type2, type2Individual);

        namespaceContains = ontology.codeModel().getProperty(FamixURIs.NAMESPACE_CONTAINS);
        hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
    }

    @Test
    public void testTop() {
        Namespace namespace = Namespace.TOP;

        namespace.modelIn(ontology, Arrays.asList(type1, type2));

        Individual individual =
                ontology.codeModel().getIndividual(prefix + Namespace.TOP.getName());

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, ""));
        assertTrue(ontology.codeModel().contains(individual, namespaceContains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual, namespaceContains, type2Individual));
    }

    @Test
    public void testNestedNamespace() {
        Namespace namespace =
                new Namespace("namespace.subnamespace", new Namespace("namespace", Namespace.TOP));

        namespace.modelIn(ontology, Arrays.asList(type1, type2));

        Individual individual0 = ontology.codeModel().getIndividual(prefix + "");
        Individual individual1 = ontology.codeModel().getIndividual(prefix + "namespace");
        Individual individual2 =
                ontology.codeModel().getIndividual(prefix + "namespace.subnamespace");

        assertNotNull(individual0);
        assertNotNull(individual1);
        assertNotNull(individual2);
        assertTrue(ontology.codeModel().containsLiteral(individual0, hasName, ""));
        assertTrue(ontology.codeModel().containsLiteral(individual1, hasName, "namespace"));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual2, hasName, "namespace.subnamespace"));
        assertTrue(ontology.codeModel().contains(individual2, namespaceContains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual2, namespaceContains, type2Individual));
    }

    @Test
    public void testRecreationOfNamespaceIndividual() {
        Namespace n1 = new Namespace("namespace", Namespace.TOP);
        Namespace n2 = new Namespace("namespace.subnamespace", n1);

        n1.modelIn(ontology, Arrays.asList(type1));
        n2.modelIn(ontology, Arrays.asList(type2));

        Individual individual0 =
                ontology.codeModel().getIndividual(prefix + Namespace.TOP.getName());
        Individual individual1 = ontology.codeModel().getIndividual(prefix + n1.getName());
        Individual individual2 = ontology.codeModel().getIndividual(prefix + n2.getName());

        assertNotNull(individual0);
        assertNotNull(individual1);
        assertNotNull(individual2);
        assertTrue(ontology.codeModel().containsLiteral(individual0, hasName, ""));
        assertTrue(ontology.codeModel().containsLiteral(individual1, hasName, "namespace"));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual2, hasName, "namespace.subnamespace"));
        assertTrue(ontology.codeModel().contains(individual1, namespaceContains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual2, namespaceContains, type2Individual));
    }
}
