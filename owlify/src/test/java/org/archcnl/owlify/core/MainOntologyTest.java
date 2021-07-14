package org.archcnl.owlify.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.core.MainOntology.MainDatatypeProperties;
import org.archcnl.owlify.core.MainOntology.MainObjectProperties;
import org.archcnl.owlify.core.MainOntology.MainOntClasses;
import org.junit.Before;
import org.junit.Test;

public class MainOntologyTest {

    private static final String path = "/this/is/an/example.path";
    private Individual content1;
    private Individual content2;
    private MainOntology mainOnt;

    @Before
    public void setUp() throws FileNotFoundException {
        OntModel myOnt = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

        final OntClass class1 = myOnt.createClass("http://test.owl#ClassA");
        final OntClass class2 = myOnt.createClass("http://test.owl#ClassB");

        mainOnt = new MainOntology(new FileInputStream("src/main/resources/ontologies/main.owl"));
        content1 = class1.createIndividual("InstanceA");
        content2 = class2.createIndividual("InstanceB");
    }

    @Test
    public void testModelSoftwareArtifacts() {
        mainOnt.modelSoftwareArtifactFile(path, Arrays.asList(content1, content2));

        OntModel main = mainOnt.getOntology();
        Individual artifact =
                main.getIndividual(MainOntClasses.SoftwareArtifactFile.uri() + "/" + path);
        ObjectProperty contains =
                main.getObjectProperty(MainObjectProperties.containsArtifact.uri());
        DatatypeProperty hasPath = main.getDatatypeProperty(MainDatatypeProperties.hasPath.uri());

        assertNotNull(artifact);
        assertTrue(mainOnt.getOntology().contains(artifact, contains, content1));
        assertTrue(mainOnt.getOntology().contains(artifact, contains, content2));
        assertTrue(mainOnt.getOntology().containsLiteral(artifact, hasPath, path));
    }

    @Test
    public void testModelEmptySoftwareArtifact() {
        mainOnt.modelSoftwareArtifactFile(path, new ArrayList<Individual>());

        OntModel main = mainOnt.getOntology();
        Individual artifact =
                main.getIndividual(MainOntClasses.SoftwareArtifactFile.uri() + "/" + path);
        ObjectProperty contains =
                main.getObjectProperty(MainObjectProperties.containsArtifact.uri());
        DatatypeProperty hasPath = main.getDatatypeProperty(MainDatatypeProperties.hasPath.uri());

        assertNotNull(artifact);
        assertFalse(mainOnt.getOntology().contains(artifact, contains, content1));
        assertFalse(mainOnt.getOntology().contains(artifact, contains, content2));
        assertTrue(mainOnt.getOntology().containsLiteral(artifact, hasPath, path));
    }

    @Test
    public void testURIsMatchURIsFromOntologyFile() throws FileNotFoundException {
        OntModel baseOnt = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        baseOnt.read(new FileInputStream("src/main/resources/ontologies/main.owl"), null);

        for (MainOntClasses ontClass : MainOntClasses.values()) {
            assertNotNull(
                    ontClass.name() + " from MainOntClasses has no associated class in main.owl.",
                    baseOnt.getOntClass(ontClass.uri()));
        }

        for (MainObjectProperties prop : MainObjectProperties.values()) {
            assertNotNull(
                    prop.name()
                            + " from MainObjectProperties has no associated object property in main.owl.",
                    baseOnt.getObjectProperty(prop.uri()));
        }

        for (MainDatatypeProperties prop : MainDatatypeProperties.values()) {
            assertNotNull(
                    prop.name()
                            + " from MainDatatypeProperties has no associated datatype property in main.owl.",
                    baseOnt.getDatatypeProperty(prop.uri()));
        }
    }
}
