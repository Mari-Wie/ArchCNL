package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationTypeAttribute;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class AnnotationTest {

    private FamixOntology ontology;

    private static final String name = "MyAnnotation";
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
        List<AnnotationAttribute> attributes = Arrays.asList(DummyObjects.annotationAttribute());

        DefinedType type =
                new Annotation(
                        "TODO", fullName, name, new ArrayList<>(), new ArrayList<>(), attributes);

        type.firstPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(AnnotationType.individualUri(fullName));

        assertNotNull(individual);

        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasFullQualifiedName), fullName));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, ontology.get(isExternal), false));
        assertEquals(AnnotationType.uri(), individual.getOntClass().getURI());
        assertNotNull(
                ontology.codeModel()
                        .getProperty(individual, ontology.get(hasAnnotationTypeAttribute)));

        assertTrue(ontology.typeCache().isDefined(fullName));
        assertSame(individual, ontology.typeCache().getIndividual(fullName));
    }

    @Test
    public void testSecondPass() {
        List<AnnotationInstance> annotations = Arrays.asList(DummyObjects.annotationInstance());
        List<Modifier> modifiers = Arrays.asList(DummyObjects.modifier());

        DefinedType type =
                new Annotation("TODO", fullName, name, annotations, modifiers, new ArrayList<>());

        type.firstPass(ontology);
        type.secondPass(ontology);

        Individual individual =
                ontology.codeModel().getIndividual(AnnotationType.individualUri(fullName));

        assertNotNull(individual);
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }

    @Test
    public void testGetNestedTypeNames() {
        DefinedType type =
                new Annotation(
                        "TODO",
                        fullName,
                        name,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        assertEquals(1, type.getNestedTypeNames().size());
        assertEquals(fullName, type.getNestedTypeNames().get(0));
    }
}
