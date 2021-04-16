package org.archcnl.owlify.famix;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.owlify.famix.parser.FamixOntologyTransformer;
import org.junit.Test;

public class FlakyTest {

    @Test
    public void test() throws IOException {
        FamixOntologyTransformer famixTransformer =
                new FamixOntologyTransformer("./src/test/resources/results.owl");
        Path testSourcePath = Paths.get("./src/test/resources/project");
        famixTransformer.addSourcePath(testSourcePath);
        famixTransformer.transform();

        // assert that the models are isomorphic
        OntModel expected = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        expected.read(this.getClass().getResourceAsStream("/results-expected.owl"), null);

        OntModel actual = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        actual.read(new FileInputStream(famixTransformer.getResultPath()), null);
        
        // workaround: remove the absolute paths
        expected.removeAll(null, expected.getProperty("http://arch-ont.org/ontologies/famix.owl#hasPath"), null);
        expected.removeAll(null, expected.getProperty("http://arch-ont.org/ontologies/main.owl#hasPath"), null);
        actual.removeAll(null, actual.getProperty("http://arch-ont.org/ontologies/famix.owl#hasPath"), null);
        actual.removeAll(null, actual.getProperty("http://arch-ont.org/ontologies/main.owl#hasPath"), null);
        

        actual.write(new FileWriter("actual-without-paths"));
        
        // TODO: does not work on some systems
        assertTrue(expected.isIsomorphicWith(actual));
    }
}
