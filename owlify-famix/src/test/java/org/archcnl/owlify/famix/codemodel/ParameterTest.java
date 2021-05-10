package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Method;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesParameter;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class ParameterTest {

    private FamixOntology ontology;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));
    }

    @Test
    public void testParameter() {
        final String name = "parameter";
        final String parentName = "namespace.Class.method";
        final Type type = new Type("boolean", "boolean", true);
        final List<Modifier> modifiers = Arrays.asList(new Modifier("final"));
        final List<AnnotationInstance> annotations =
                Arrays.asList(new AnnotationInstance("Deprecated", new ArrayList<>()));

        Parameter param = new Parameter(name, type, modifiers, annotations);
        Individual method = ontology.createIndividual(Method, parentName);

        param.modelIn(ontology, parentName, method);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Parameter.individualUri(parentName + "." + name));

        assertNotNull(individual);
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(hasDeclaredType),
                                type.getIndividual(ontology)));
        assertTrue(
                ontology.codeModel().contains(method, ontology.get(definesParameter), individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }
}
