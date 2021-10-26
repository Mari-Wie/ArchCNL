package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationInstanceAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasValue;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstanceAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationTypeAttribute;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class AnnotationMemberValuePairTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testKnownAttribute() {
        final String name = "attribute";
        final String value = "5";
        final String annotationName = "SomeAnnotation";
        final String parentURI = "SomeClass." + annotationName;
        AnnotationMemberValuePair pair = new AnnotationMemberValuePair(name, value);
        Individual annotation = ontology.createIndividual(AnnotationInstance, parentURI);
        Individual attributeIndividual =
                ontology.createIndividual(AnnotationTypeAttribute, "attribute");

        ontology.annotationAttributeCache()
                .addAnnotationAttribute(annotationName, name, attributeIndividual);
        pair.modelIn(ontology, annotationName, parentURI, annotation);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                AnnotationInstanceAttribute.individualUri(parentURI + "-" + name));

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(hasAnnotationTypeAttribute),
                                attributeIndividual));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                annotation,
                                ontology.get(hasAnnotationInstanceAttribute),
                                individual));
        assertTrue(ontology.codeModel().contains(individual, ontology.get(hasValue), value));
    }

    @Test
    public void testUnknownAttribute() {
        final String name = "attribute";
        final String value = "5";
        final String annotationName = "SomeAnnotation";
        final String parentURI = "SomeClass." + annotationName;
        AnnotationMemberValuePair pair = new AnnotationMemberValuePair(name, value);
        Individual annotation = ontology.createIndividual(AnnotationInstance, parentURI);

        pair.modelIn(ontology, annotationName, parentURI, annotation);

        assertTrue(ontology.annotationAttributeCache().isKnownAttribute(annotationName, name));

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                AnnotationInstanceAttribute.individualUri(parentURI + "-" + name));
        Individual attributeIndividual =
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name);

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(hasAnnotationTypeAttribute),
                                attributeIndividual));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                annotation,
                                ontology.get(hasAnnotationInstanceAttribute),
                                individual));
        assertTrue(ontology.codeModel().contains(individual, ontology.get(hasValue), value));
    }
}
