package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class AnnotationInstanceTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testKnownAnnotationType() {
        final String name = "namespace.SomeAnnotation";
        final List<AnnotationMemberValuePair> values =
                Arrays.asList(new AnnotationMemberValuePair("attribute", "value"));
        AnnotationInstance instance = new AnnotationInstance(name, values);
        Individual parent =
                ontology.codeModel()
                        .getOntClass(FamixURIs.METHOD)
                        .createIndividual("namespace.Class.method");
        Individual type =
                ontology.codeModel().getOntClass(FamixURIs.ANNOTATION_TYPE).createIndividual(name);

        ontology.typeCache().addDefinedType(name, type);

        instance.modelIn(ontology, parent);

        Property hasAnnotationType =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE);
        Property hasAnnotationInstanceAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE);
        Individual individual =
                ontology.codeModel()
                        .getIndividual("namespace.Class.method-namespace.SomeAnnotation");

        assertNotNull(individual);
        assertEquals(FamixURIs.ANNOTATION_INSTANCE, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().contains(individual, hasAnnotationType, type));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstanceAttribute));
    }

    @Test
    public void testUnknownAnnotationType() {
        final String name = "namespace.SomeAnnotation";
        final List<AnnotationMemberValuePair> values =
                Arrays.asList(new AnnotationMemberValuePair("attribute", "value"));
        AnnotationInstance instance = new AnnotationInstance(name, values);
        Individual parent =
                ontology.codeModel()
                        .getOntClass(FamixURIs.METHOD)
                        .createIndividual("namespace.Class.method");

        instance.modelIn(ontology, parent);

        Property hasAnnotationType =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE);
        Property hasAnnotationInstanceAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE);
        Property isExternal = ontology.codeModel().getProperty(FamixURIs.IS_EXTERNAL);
        Property hasFullQualifiedName =
                ontology.codeModel().getProperty(FamixURIs.HAS_FULL_QUALIFIED_NAME);
        Individual individual =
                ontology.codeModel()
                        .getIndividual("namespace.Class.method-namespace.SomeAnnotation");
        Individual type = ontology.codeModel().getIndividual(name);

        assertNotNull(type);
        assertTrue(ontology.typeCache().isDefined(name));
        assertSame(type, ontology.typeCache().getIndividual(name));
        assertEquals(FamixURIs.ANNOTATION_TYPE, type.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(type, isExternal, true));
        assertTrue(ontology.codeModel().containsLiteral(type, hasFullQualifiedName, name));

        assertNotNull(individual);
        assertEquals(FamixURIs.ANNOTATION_INSTANCE, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().contains(individual, hasAnnotationType, type));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstanceAttribute));
    }
}
