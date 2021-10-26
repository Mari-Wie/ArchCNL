package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationTypeAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class AnnotationAttributeTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testAnnotationAttributeTransformation() {
        final String name = "attribute";
        final String annotationName = "namespace.SomeAnnotation";
        final Type type = new Type("Type", "t", false);

        AnnotationAttribute attribute = new AnnotationAttribute(name, type);
        Individual typeIndividual = ontology.createIndividual(FamixClass, type.getName());
        Individual annotation = ontology.createIndividual(AnnotationType, annotationName);

        ontology.typeCache().addDefinedType(type.getName(), typeIndividual);
        attribute.modelIn(ontology, annotationName, annotation);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                AnnotationTypeAttribute.individualUri(annotationName + "/" + name));

        assertNotNull(individual);
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual, ontology.get(hasDeclaredType), typeIndividual));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                annotation, ontology.get(hasAnnotationTypeAttribute), individual));

        assertTrue(ontology.annotationAttributeCache().isKnownAttribute(annotationName, name));
        assertSame(
                individual,
                ontology.annotationAttributeCache().getAnnotationAttribute(annotationName, name));
    }
}
