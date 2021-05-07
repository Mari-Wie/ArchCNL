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

public class AnnotationMemberValuePairTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testKnownAttribute() {
        final String name = "attribute";
        final String value = "5";
        final String annotationName = "SomeAnnotation";
        final String parentURI = "SomeClass." + annotationName;
        AnnotationMemberValuePair pair = new AnnotationMemberValuePair(name, value);
        Individual annotation =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_INSTANCE)
                        .createIndividual(parentURI);
        Individual attributeIndividual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE_ATTRIBUTE)
                        .createIndividual();

        ontology.annotationAttributeCache()
                .addAnnotationAttribute(annotationName, name, attributeIndividual);
        pair.modelIn(ontology, annotationName, annotation);

        Property hasAnnotationTypeAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE);
        Property hasAnnotationInstanceAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE);
        Property hasValue = ontology.codeModel().getProperty(FamixURIs.HAS_VALUE);
        Individual individual = ontology.codeModel().getIndividual(parentURI + "-" + name);

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .contains(individual, hasAnnotationTypeAttribute, attributeIndividual));
        assertTrue(
                ontology.codeModel()
                        .contains(annotation, hasAnnotationInstanceAttribute, individual));
        assertTrue(ontology.codeModel().contains(individual, hasValue, value));
    }

    @Test
    public void testUnknownAttribute() {
        final String name = "attribute";
        final String value = "5";
        final String annotationName = "SomeAnnotation";
        final String parentURI = "SomeClass." + annotationName;
        AnnotationMemberValuePair pair = new AnnotationMemberValuePair(name, value);
        Individual annotation =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_INSTANCE)
                        .createIndividual(parentURI);

        pair.modelIn(ontology, annotationName, annotation);

        Property hasAnnotationTypeAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE);
        Property hasAnnotationInstanceAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE_ATTRIBUTE);
        Property hasValue = ontology.codeModel().getProperty(FamixURIs.HAS_VALUE);
        Individual individual = ontology.codeModel().getIndividual(parentURI + "-" + name);
        Individual attributeIndividual =
                ontology.codeModel().getIndividual(annotationName + "-" + name);

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .contains(individual, hasAnnotationTypeAttribute, attributeIndividual));
        assertTrue(
                ontology.codeModel()
                        .contains(annotation, hasAnnotationInstanceAttribute, individual));
        assertTrue(ontology.codeModel().contains(individual, hasValue, value));
        assertTrue(ontology.annotationAttributeCache().isKnownAttribute(annotationName, name));
    }
}
