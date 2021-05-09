package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntologyNew.FamixClasses.AnnotationType;
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
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
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
                        collidingName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
                                FamixOntologyNew.FamixClasses.Namespace.individualUri(
                                        collidingName));
        Property namespaceContains = ontology.codeModel().getProperty(FamixURIs.NAMESPACE_CONTAINS);

        assertNotNull(typeIndividual);
        assertNotNull(namespaceIndividual);
        assertNotSame(typeIndividual, namespaceIndividual);

        assertEquals(FamixURIs.ANNOTATION_TYPE, typeIndividual.getOntClass().getURI());
        assertEquals(FamixURIs.NAMESPACE, namespaceIndividual.getOntClass().getURI());

        // only the namespace has a "namespaceContains" property
        assertNull(ontology.codeModel().getProperty(typeIndividual, namespaceContains));
        assertNotNull(ontology.codeModel().getProperty(namespaceIndividual, namespaceContains));
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
        public void modelFirstPass(FamixOntologyNew ont) {
            firstPassCalled = true;
        }

        @Override
        public void modelSecondPass(FamixOntologyNew ont) {
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
