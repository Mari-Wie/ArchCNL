package org.archcnl.owlify.git.ontology;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.jena.rdf.model.Model;
import org.junit.Assert;
import org.junit.Test;

public class GitOntologyTransformerTest {

    @Test
    public void testOntologyCreationBasedOnArchCNLProject() throws FileNotFoundException {
        GitOntologyTransformer transformer = new GitOntologyTransformer();
        // when
        Model model = transformer.transform(Arrays.asList(Paths.get("").toAbsolutePath()));
        // then
        Assert.assertNotNull(model);
    }
}
