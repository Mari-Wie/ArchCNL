package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.Inheritance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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

public class ClassOrInterfaceTest {

    private FamixOntologyNew ontology;

    private static final String name = "MyClassOrInterface";
    private static final String fullName = "namespace." + name;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testFirstPass() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
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
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasFullQualifiedName =
                ontology.codeModel().getProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME);
        Property isExternal = ontology.codeModel().getProperty(FamixURIs.IS_EXTERNAL);

        assertNotNull(individual);
        //        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name)); //
        // TODO
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasFullQualifiedName, fullName));
        assertTrue(ontology.codeModel().containsLiteral(individual, isExternal, false));
        assertEquals(FamixURIs.FAMIX_CLASS, individual.getOntClass().getURI());

        assertTrue(ontology.typeCache().isDefined(fullName));
        assertSame(individual, ontology.typeCache().getIndividual(fullName));
    }

    @Test
    public void testFirstPassNestedTypesAreVisited() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterface,
                        Arrays.asList(DummyObjects.referenceType()));

        type.firstPass(ontology);

        String nestedName = DummyObjects.definedType().getName();

        Individual individual =
                ontology.codeModel().getIndividual(FamixClass.individualUri(nestedName));
        Property hasFullQualifiedName =
                ontology.codeModel().getProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME);
        Property isExternal = ontology.codeModel().getProperty(FamixURIs.IS_EXTERNAL);

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasFullQualifiedName, nestedName));
        assertTrue(ontology.codeModel().containsLiteral(individual, isExternal, false));
        assertEquals(FamixURIs.FAMIX_CLASS, individual.getOntClass().getURI());

        assertTrue(ontology.typeCache().isDefined(nestedName));
        assertSame(individual, ontology.typeCache().getIndividual(nestedName));
    }

    @Test
    public void testSecondPass() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
                        Arrays.asList(DummyObjects.definedType()),
                        Arrays.asList(DummyObjects.method()),
                        Arrays.asList(DummyObjects.field()),
                        Arrays.asList(DummyObjects.modifier()),
                        Arrays.asList(DummyObjects.annotationInstance()),
                        isInterface,
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
        Property definesMethod = ontology.codeModel().getProperty(FamixURIs.DEFINES_METHOD);
        Property definesAttribute = ontology.codeModel().getProperty(FamixURIs.DEFINES_ATTRIBUTE);
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);
        Property isInterfaceProp = ontology.codeModel().getProperty(FamixURIs.IS_INTERFACE);
        Property hasSubclass = ontology.codeModel().getProperty(FamixURIs.HAS_SUBCLASS);
        Property hasSuperclass = ontology.codeModel().getProperty(FamixURIs.HAS_SUPERCLASS);

        assertNotNull(individual);
        assertNotNull(inheritance);
        assertTrue(ontology.codeModel().containsLiteral(individual, isInterfaceProp, isInterface));
        assertTrue(ontology.codeModel().contains(inheritance, hasSuperclass, supertype));
        assertTrue(ontology.codeModel().contains(inheritance, hasSubclass, individual));
        assertNotNull(ontology.codeModel().getProperty(individual, definesMethod));
        assertNotNull(ontology.codeModel().getProperty(individual, definesAttribute));
        assertNotNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));
    }

    @Test
    public void testGetNestedTypeNames() {
        final boolean isInterface = false;
        DefinedType type =
                new ClassOrInterface(
                        fullName,
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
