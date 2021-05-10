package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.namespaceContains;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class NamespaceTest {

    private FamixOntology ontology;
    private static final String type1 = "namespace.SomeType";
    private static final String type2 = "namespace.SomeOtherType";

    private Individual type1Individual;
    private Individual type2Individual;
    private Property contains;
    private Property named;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));

        type1Individual = ontology.createIndividual(FamixClass, type1);
        type2Individual = ontology.createIndividual(FamixClass, type2);

        ontology.typeCache().addDefinedType(type1, type1Individual);
        ontology.typeCache().addDefinedType(type2, type2Individual);

        contains = ontology.get(namespaceContains);
        named = ontology.get(hasName);
    }

    @Test
    public void testTop() {
        Namespace namespace = Namespace.TOP;

        namespace.modelIn(ontology, Arrays.asList(type1, type2));

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Namespace.individualUri(Namespace.TOP.getName()));

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, named, ""));
        assertTrue(ontology.codeModel().contains(individual, contains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual, contains, type2Individual));
    }

    @Test
    public void testNestedNamespace() {
        Namespace namespace =
                new Namespace("namespace.subnamespace", new Namespace("namespace", Namespace.TOP));

        namespace.modelIn(ontology, Arrays.asList(type1, type2));

        Individual individual0 =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Namespace.individualUri(Namespace.TOP.getName()));
        Individual individual1 =
                ontology.codeModel()
                        .getIndividual(FamixClasses.Namespace.individualUri("namespace"));
        Individual individual2 =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Namespace.individualUri("namespace.subnamespace"));

        assertNotNull(individual0);
        assertNotNull(individual1);
        assertNotNull(individual2);
        assertTrue(ontology.codeModel().containsLiteral(individual0, named, ""));
        assertTrue(ontology.codeModel().containsLiteral(individual1, named, "namespace"));
        assertTrue(
                ontology.codeModel().containsLiteral(individual2, named, "namespace.subnamespace"));
        assertTrue(ontology.codeModel().contains(individual2, contains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual2, contains, type2Individual));
    }

    @Test
    public void testRecreationOfNamespaceIndividual() {
        Namespace n1 = new Namespace("namespace", Namespace.TOP);
        Namespace n2 = new Namespace("namespace.subnamespace", n1);

        n1.modelIn(ontology, Arrays.asList(type1));
        n2.modelIn(ontology, Arrays.asList(type2));

        Individual individual0 =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Namespace.individualUri(Namespace.TOP.getName()));
        Individual individual1 =
                ontology.codeModel()
                        .getIndividual(FamixClasses.Namespace.individualUri(n1.getName()));
        Individual individual2 =
                ontology.codeModel()
                        .getIndividual(FamixClasses.Namespace.individualUri(n2.getName()));

        assertNotNull(individual0);
        assertNotNull(individual1);
        assertNotNull(individual2);
        assertTrue(ontology.codeModel().containsLiteral(individual0, named, ""));
        assertTrue(ontology.codeModel().containsLiteral(individual1, named, "namespace"));
        assertTrue(
                ontology.codeModel().containsLiteral(individual2, named, "namespace.subnamespace"));
        assertTrue(ontology.codeModel().contains(individual1, contains, type1Individual));
        assertTrue(ontology.codeModel().contains(individual2, contains, type2Individual));
    }
}
