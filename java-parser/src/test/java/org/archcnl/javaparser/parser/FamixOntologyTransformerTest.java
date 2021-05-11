package org.archcnl.javaparser.parser;
// package org.archcnl.owlify.famix.parser;
//
// import static org.junit.Assert.assertTrue;
//
// import java.io.FileInputStream;
// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.stream.Stream;
// import org.apache.jena.ontology.OntModel;
// import org.apache.jena.ontology.OntModelSpec;
// import org.apache.jena.rdf.model.ModelFactory;
// import org.junit.Before;
// import org.junit.Test;
//
// public class FamixOntologyTransformerTest {
//
//    @Before
//    public void setUp() throws Exception {}
//
//    @Test
//    public void testCoarse() throws IOException {
//        FamixOntologyTransformer famixTransformer = null; // TODO
//                //new FamixOntologyTransformer("./src/test/resources/results.owl");
//        Path testSourcePath = Paths.get("./src/test/resources/project");
//        famixTransformer.addSourcePath(testSourcePath);
//        famixTransformer.transform();
//
//        // The output ontology contains some "tags" <hasPath> ... </hasPath> which contain
//        // absolute paths. They have to be removed to enable testing on different machines.
//        // Note that they have been already removed from the "expected" file manually.
//        removeHasPathTagsFromFile(famixTransformer.getResultPath());
//
//        // assert that the models are isomorphic
//        OntModel expected = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
//        expected.read(this.getClass().getResourceAsStream("/results-expected.owl"), null);
//
//        OntModel actual = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
//        actual.read(new FileInputStream(famixTransformer.getResultPath()), null);
//
//        // workaround: remove the absolute paths
//
// expected.getProperty("http://arch-ont.org/ontologies/main.owl#hasPath").removeProperties();
//        actual.getProperty("http://arch-ont.org/ontologies/main.owl#hasPath").removeProperties();
//
//        // TODO: does not work on some systems
//        // assertTrue(expected.isIsomorphicWith(actual));
//    }
//
//    private boolean skip = false;
//
//    private void removeHasPathTagsFromFile(String filepath) throws IOException {
//        StringBuilder builder = new StringBuilder();
//
//        // remove all lines enclosed by <hasPath> ... </hasPath>
//        try (Stream<String> stream = Files.lines(Paths.get(filepath), StandardCharsets.UTF_8)) {
//            stream.forEach(
//                    s -> {
//                        if (s.contains("<hasPath>")) {
//                            skip = true;
//                        }
//                        if (!skip) {
//                            builder.append(s).append("\n");
//                        }
//                        if (s.contains("</hasPath>")) {
//                            skip = false;
//                        }
//                    });
//        }
//
//        Files.writeString(Paths.get(filepath), builder.toString(), StandardCharsets.UTF_8);
//    }
// }
