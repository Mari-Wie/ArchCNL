package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.imports;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.core.MainOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.junit.Before;
import org.junit.Test;

public class SourceFileTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testFirstPass() {
        Path path = Paths.get("somepath");
        SourceFile file =
                new SourceFile(
                        path,
                        Arrays.asList(DummyObjects.definedType()),
                        DummyObjects.namespace(),
                        Arrays.asList(DummyObjects.referenceType()));

        file.modelFirstPass(ontology);
        // user defined types must be known now
        assertTrue(ontology.typeCache().isDefined(DummyObjects.definedType().getName()));
    }

    @Test
    public void testSecondPass() {
        Path path = Paths.get("somepath");
        SourceFile file =
                new SourceFile(
                        path,
                        Arrays.asList(DummyObjects.definedType()),
                        DummyObjects.namespace(),
                        Arrays.asList(DummyObjects.referenceType()));

        file.modelFirstPass(ontology);
        file.modelSecondPass(ontology);

        Individual type = ontology.typeCache().getIndividual(DummyObjects.definedType().getName());
        Individual importedType = DummyObjects.referenceType().getIndividual(ontology);

        assertTrue(ontology.codeModel().contains(type, ontology.get(imports), importedType));

        Individual individual =
                ontology.mainModel()
                        .getOntology()
                        .getIndividual(
                                MainOntology.namespace
                                        + MainOntology.MainOntClasses.SoftwareArtifactFile.name()
                                        + "/"
                                        + path);
        Property fileContains =
                MainOntology.MainObjectProperties.containsArtifact.get(
                        ontology.mainModel().getOntology());

        assertNotNull(individual);
        assertTrue(ontology.mainModel().getOntology().contains(individual, fileContains, type));
    }
}
