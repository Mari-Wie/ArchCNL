package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.AnnotationType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.namespaceContains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testProjectTransformation() {
        Project project = new Project();

        SourceFileMockup fileMock1 = new SourceFileMockup();
        SourceFileMockup fileMock2 = new SourceFileMockup();

        project.addFile(fileMock1);
        project.addFile(fileMock2);

        project.modelIn(ontology);

        assertTrue(fileMock1.firstPassDone());
        assertTrue(fileMock2.firstPassDone());
        assertTrue(fileMock1.secondPassDone());
        assertTrue(fileMock2.secondPassDone());
    }

    @Test
    public void testCollidingNames() {
        final String collidingName = "collision";

        // an annotation "@collision"
        DefinedType type =
                new Annotation(
                        collidingName,
                        collidingName,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        // a namespace "collision"
        Namespace namespace = new Namespace(collidingName, Namespace.TOP);

        SourceFile file1 =
                new SourceFile(
                        Paths.get("file 1"), Arrays.asList(type), Namespace.TOP, new ArrayList<>());
        SourceFile file2 =
                new SourceFile(
                        Paths.get("file 1"),
                        Arrays.asList(DummyObjects.definedType()),
                        namespace,
                        new ArrayList<>());

        Project project = new Project();
        project.addFile(file1);
        project.addFile(file2);

        project.modelIn(ontology);

        Individual typeIndividual =
                ontology.codeModel().getIndividual(AnnotationType.individualUri(collidingName));
        Individual namespaceIndividual =
                ontology.codeModel()
                        .getIndividual(
                                FamixOntology.FamixClasses.Namespace.individualUri(collidingName));

        assertNotNull(typeIndividual);
        assertNotNull(namespaceIndividual);
        assertNotSame(typeIndividual, namespaceIndividual);

        assertEquals(AnnotationType.uri(), typeIndividual.getOntClass().getURI());
        assertEquals(FamixClasses.Namespace.uri(), namespaceIndividual.getOntClass().getURI());

        // only the namespace has a "namespaceContains" property
        assertNull(
                ontology.codeModel().getProperty(typeIndividual, ontology.get(namespaceContains)));
        assertNotNull(
                ontology.codeModel()
                        .getProperty(namespaceIndividual, ontology.get(namespaceContains)));
    }

    static class SourceFileMockup extends SourceFile {
        private boolean firstPassCalled;
        private boolean secondPassCalled;

        public SourceFileMockup() {
            super(
                    Paths.get("MOCKUP"),
                    Arrays.asList(DummyObjects.definedType()),
                    DummyObjects.namespace(),
                    Arrays.asList(DummyObjects.referenceType()));
            firstPassCalled = false;
            secondPassCalled = false;
        }

        @Override
        public void modelFirstPass(FamixOntology ont) {
            firstPassCalled = true;
        }

        @Override
        public void modelSecondPass(FamixOntology ont) {
            secondPassCalled = true;
        }

        public boolean firstPassDone() {
            return firstPassCalled;
        }

        public boolean secondPassDone() {
            return secondPassCalled;
        }
    }
}
