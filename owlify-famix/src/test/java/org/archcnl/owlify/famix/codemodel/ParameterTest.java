package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class ParameterTest {

    private FamixOntologyNew ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testParameter() {
        final String name = "parameter";
        final Type type = new Type("boolean", true);
        final List<Modifier> modifiers = Arrays.asList(new Modifier("final"));
        final List<AnnotationInstance> annotations =
                Arrays.asList(new AnnotationInstance("Deprecated", new ArrayList<>()));

        Parameter param = new Parameter(name, type, modifiers, annotations);

        Individual method =
                ontology.codeModel()
                        .getOntClass(FamixURIs.METHOD)
                        .createIndividual("namespace.Class.method");

        param.modelIn(ontology, method);

        Individual individual =
                ontology.codeModel().getIndividual("namespace.Class.method.parameter");
        Individual typeIndividual =
                ontology.codeModel().getIndividual(FamixURIs.PREFIX + type.getName());
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property definesParameter = ontology.codeModel().getProperty(FamixURIs.DEFINES_PARAMETER);
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);

        assertNotNull(individual);
        assertNotNull(typeIndividual);
        assertTrue(ontology.codeModel().contains(individual, hasDeclaredType, typeIndividual));
        assertTrue(ontology.codeModel().contains(method, definesParameter, individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name));
        assertNotNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));
    }
}
