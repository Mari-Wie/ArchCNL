package org.archcnl.conformancechecking.impl;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.archcnl.conformancechecking.impl.ConformanceCheckOntologyClassesAndProperties.ConformanceCheckObjectProperties;
import org.junit.Test;

public class ConformanceCheckOntologyClassesAndPropertiesTest {

    @Test
    public void testURIsMatchURIsFromOntologyFile() throws FileNotFoundException {
        OntModel baseOnt = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        baseOnt.read(
                new FileInputStream("src/main/resources/ontologies/architectureconformance.owl"),
                null);

        // TODO: uncomment tests when refactoring issues are done

        //        for (ConformanceCheckClassesProperties ontClass :
        // ConformanceCheckClassesProperties.values()) {
        //            assertNotNull(ontClass.name() + " from ConformanceCheckClassesProperties has
        // no associated class in architectureconformance.owl.",
        // baseOnt.getOntClass(ontClass.getUri()));
        //        }

        for (ConformanceCheckObjectProperties prop : ConformanceCheckObjectProperties.values()) {
            assertNotNull(
                    prop.name()
                            + " from ConformanceCheckObjectProperties has no associated object property in architectureconformance.owl.",
                    baseOnt.getObjectProperty(prop.getUri()));
        }

        //        for (ConformanceCheckDatatypeProperties prop :
        // ConformanceCheckDatatypeProperties.values()) {
        //            assertNotNull(prop.name() + " from ConformanceCheckDatatypeProperties has no
        // associated datatype property in architectureconformance.owl.",
        // baseOnt.getDatatypeProperty(prop.getUri()));
        //        }
    }
}
