package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Inheritance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isInterface;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesMethod;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasSubClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasSuperClass;
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

public class ClassOrInterfaceTest {

    private FamixOntology ontology;

    private static final String name = "MyClassOrInterface";
    private static final String fullName = "namespace." + name;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testFirstPass() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterface,
                        Arrays.asList(DummyObjects.referenceType()));

        type.firstPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(FamixClass.individualUri(fullName));

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasFullQualifiedName), fullName));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, ontology.get(isExternal), false));
        assertEquals(FamixClass.uri(), individual.getOntClass().getURI());

        assertTrue(ontology.typeCache().isDefined(fullName));
        assertSame(individual, ontology.typeCache().getIndividual(fullName));
    }

    @Test
    public void testFirstPassNestedTypesAreVisited() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterface,
                        Arrays.asList(DummyObjects.referenceType()));

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
        final boolean isInterfaceFlag = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterfaceFlag,
                        Arrays.asList(DummyObjects.referenceType()));

        type.firstPass(ontology);
        type.secondPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(FamixClass.individualUri(fullName));
        Individual supertype = DummyObjects.referenceType().getIndividual(ontology);
        Individual inheritance =
                ontology.codeModel()
                        .getIndividual(
                                Inheritance.individualUri(
                                        fullName + "-" + DummyObjects.referenceType().getName()));

        assertNotNull(individual);
        assertNotNull(inheritance);
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(isInterface), isInterfaceFlag));
        assertTrue(
                ontology.codeModel().contains(inheritance, ontology.get(hasSuperClass), supertype));
        assertTrue(
                ontology.codeModel().contains(inheritance, ontology.get(hasSubClass), individual));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesMethod)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesAttribute)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }

    @Test
    public void testGetNestedTypeNames() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        name,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterface,
                        Arrays.asList(DummyObjects.referenceType()));
        String nestedName = DummyObjects.definedType().getName();

        assertEquals(2, type.getNestedTypeNames().size());
        assertTrue(type.getNestedTypeNames().contains(fullName));
        assertTrue(type.getNestedTypeNames().contains(nestedName));
    }
}
