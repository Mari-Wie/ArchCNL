package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class LocalVariableTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testLocalVariableTransformation() {
        Type type = new Type("Type", false);
        LocalVariable variable = new LocalVariable(type, "i");
        Individual method =
                ontology.codeModel()
                        .getOntClass(FamixURIs.METHOD)
                        .createIndividual("SomeClass.someMethod");
        Property definesVariable = ontology.codeModel().getProperty(FamixURIs.DEFINES_VARIABLE);
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Individual typeIndividual =
                ontology.codeModel().getOntClass(FamixURIs.FAMIX_CLASS).createIndividual("Type");

        ontology.typeCache().addDefinedType(type.getName(), typeIndividual);
        variable.modelIn(ontology, method);

        Individual individual = ontology.codeModel().getIndividual("SomeClass.someMethod.i");

        assertNotNull(individual);
        assertEquals(FamixURIs.LOCAL_VARIABLE, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().contains(method, definesVariable, individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, "i"));
        assertTrue(ontology.codeModel().contains(individual, hasDeclaredType, typeIndividual));
    }
}
