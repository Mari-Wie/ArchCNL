package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.*;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class AnnotationTest {

    private FamixOntologyNew ontology;

    private static final String name = "MyAnnotation";
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
        List<AnnotationAttribute> attributes = Arrays.asList(DummyObjects.annotationAttribute());

        DefinedType type =
                new Annotation(fullName, new ArrayList<>(), new ArrayList<>(), attributes);

        type.firstPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(AnnotationType.individualUri(fullName));
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasFullQualifiedName =
                ontology.codeModel().getProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME);
        Property isExternal = ontology.codeModel().getProperty(FamixURIs.IS_EXTERNAL);
        Property hasAnnotationTypeAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE);

        assertNotNull(individual);
        //        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name)); //
        // TODO
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasFullQualifiedName, fullName));
        assertTrue(ontology.codeModel().containsLiteral(individual, isExternal, false));
        assertEquals(FamixURIs.ANNOTATION_TYPE, individual.getOntClass().getURI());
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationTypeAttribute));

        assertTrue(ontology.typeCache().isDefined(fullName));
        assertSame(individual, ontology.typeCache().getIndividual(fullName));
    }

    @Test
    public void testSecondPass() {
        List<AnnotationInstance> annotations = Arrays.asList(DummyObjects.annotationInstance());
        List<Modifier> modifiers = Arrays.asList(DummyObjects.modifier());

        DefinedType type = new Annotation(fullName, annotations, modifiers, new ArrayList<>());

        type.firstPass(ontology);
        type.secondPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(AnnotationType.individualUri(fullName));
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);

        assertNotNull(individual);
        assertNotNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));
    }

    @Test
    public void testGetNestedTypeNames() {
        DefinedType type =
                new Annotation(fullName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        assertEquals(1, type.getNestedTypeNames().size());
        assertEquals(fullName, type.getNestedTypeNames().get(0));
    }
}
