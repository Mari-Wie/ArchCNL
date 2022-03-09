package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Method;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasFullQualifiedName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isExternal;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstanceAttribute;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.Position;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class AnnotationInstanceTest {

    private FamixOntology ontology;
    private static final Optional<Position> position = Optional.of(new Position(5, 4));
    private static final Path path = Path.of("someRootDirectory/someClassOrInterface");

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testKnownAnnotationType() {
        final String name = "namespace.SomeAnnotation";
        final List<AnnotationMemberValuePair> values =
                Arrays.asList(new AnnotationMemberValuePair("attribute", "value"));
        AnnotationInstance instance = new AnnotationInstance(name, values, path, position);
        String parentName = "namespace.Class.method";
        Individual parent = ontology.createIndividual(Method, parentName);
        Individual type = ontology.createIndividual(AnnotationType, name);

        ontology.typeCache().addDefinedType(name, type);

        instance.modelIn(ontology, parentName, parent);

        String uri =
                AnnotationInstance.individualUri("namespace.Class.method-namespace.SomeAnnotation");
        Individual individual = ontology.codeModel().getIndividual(uri);

        assertNotNull(individual);
        assertEquals(AnnotationInstance.uri(), individual.getOntClass().getURI());
        assertTrue(
                ontology.codeModel().contains(individual, ontology.get(hasAnnotationType), type));

        assertNotNull(
                ontology.codeModel()
                        .getProperty(individual, ontology.get(hasAnnotationInstanceAttribute)));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));
    }

    @Test
    public void testUnknownAnnotationType() {
        final String name = "namespace.SomeAnnotation";
        final List<AnnotationMemberValuePair> values =
                Arrays.asList(new AnnotationMemberValuePair("attribute", "value"));
        AnnotationInstance instance = new AnnotationInstance(name, values, path, position);
        String parentName = "namespace.Class.method";
        Individual parent = ontology.createIndividual(Method, parentName);

        instance.modelIn(ontology, parentName, parent);

        String uri =
                AnnotationInstance.individualUri("namespace.Class.method-namespace.SomeAnnotation");
        Individual individual = ontology.codeModel().getIndividual(uri);
        Individual type = ontology.codeModel().getIndividual(AnnotationType.individualUri(name));

        assertNotNull(type);
        assertTrue(ontology.typeCache().isDefined(name));
        assertSame(type, ontology.typeCache().getIndividual(name));
        assertEquals(AnnotationType.uri(), type.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(type, ontology.get(isExternal), true));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(type, ontology.get(hasFullQualifiedName), name));

        assertNotNull(individual);
        assertEquals(AnnotationInstance.uri(), individual.getOntClass().getURI());
        assertTrue(
                ontology.codeModel().contains(individual, ontology.get(hasAnnotationType), type));
        assertNotNull(
                ontology.codeModel()
                        .getProperty(individual, ontology.get(hasAnnotationInstanceAttribute)));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));
    }
}
