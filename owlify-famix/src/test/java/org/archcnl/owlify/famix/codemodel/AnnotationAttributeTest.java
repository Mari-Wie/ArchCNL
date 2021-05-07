package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class AnnotationAttributeTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void test() {
        final String name = "attribute";
        final String annotationName = "namespace.SomeAnnotation";
        final Type type = new Type("Type", false);

        AnnotationAttribute attribute = new AnnotationAttribute(name, type);
        Individual annotation =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE)
                        .createIndividual(annotationName);
        Individual typeIndividual =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(type.getName());

        ontology.typeCache().addDefinedType(type.getName(), typeIndividual);

        attribute.modelIn(ontology, annotationName, annotation);

        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasAnnotationTypeAttribute =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_TYPE_ATTRIBUTE);
        Individual individual =
                ontology.codeModel().getIndividual("namespace.SomeAnnotation-attribute");

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasDeclaredType, typeIndividual));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(annotation, hasAnnotationTypeAttribute, individual));

        assertTrue(ontology.annotationAttributeCache().isKnownAttribute(annotationName, name));
        assertSame(
                individual,
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name));
    }
}
