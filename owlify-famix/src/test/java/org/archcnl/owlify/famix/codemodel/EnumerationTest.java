package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Enum;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesMethod;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesNestedType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class EnumerationTest {

    private FamixOntology ontology;

    private static final String name = "MyEnumeration";
    private static final String fullName = "namespace." + name;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testFirstPass() {
        DefinedType type =
                new Enumeration(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()));

        type.firstPass(ontology);

        Individual individual = ontology.codeModel().getIndividual(Enum.individualUri(fullName));

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasFullQualifiedName), fullName));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, ontology.get(isExternal), false));
        assertEquals(Enum.uri(), individual.getOntClass().getURI());

        assertTrue(ontology.typeCache().isDefined(fullName));
        assertSame(individual, ontology.typeCache().getIndividual(fullName));
    }

    @Test
    public void testFirstPassNestedTypesAreVisited() {
        DefinedType type =
                new Enumeration(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()));

        type.firstPass(ontology);

        String nestedName = DummyObjects.definedType().getName();
        String nestedSimpleName = DummyObjects.definedType().getSimpleName();

        Individual individual =
                ontology.codeModel().getIndividual(FamixClass.individualUri(nestedName));

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasName), nestedSimpleName));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual, ontology.get(hasFullQualifiedName), nestedName));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, ontology.get(isExternal), false));
        assertEquals(FamixClass.uri(), individual.getOntClass().getURI());

        assertTrue(ontology.typeCache().isDefined(nestedName));
        assertSame(individual, ontology.typeCache().getIndividual(nestedName));
    }

    @Test
    public void testSecondPass() {
        DefinedType type =
                new Enumeration(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()));

        type.firstPass(ontology);
        type.secondPass(ontology);

        Individual individual = ontology.codeModel().getIndividual(Enum.individualUri(fullName));

        assertNotNull(individual);
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesMethod)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesAttribute)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(definesNestedType)));
    }

    @Test
    public void testGetNestedTypeNames() {
        DefinedType type =
                new Enumeration(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()));
        String nestedName = DummyObjects.definedType().getName();

        assertEquals(2, type.getNestedTypeNames().size());
        assertTrue(type.getNestedTypeNames().contains(fullName));
        assertTrue(type.getNestedTypeNames().contains(nestedName));
    }
}
