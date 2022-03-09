package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.LocalVariable;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Method;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesVariable;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class LocalVariableTest {

    private FamixOntology ontology;
    private static final Optional<Integer> position = Optional.of(5);
    private static final Path path = Path.of("someRootDirectory/someClassOrInterface");

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testLocalVariableTransformation() {
        Type type = new Type("Type", "Type", false);
        final String parentUri = "SomeClass.someMethod";
        final List<Modifier> modifiers = Arrays.asList(new Modifier("final"));
        LocalVariable variable = new LocalVariable(type, "i", modifiers, path, position);
        Individual method = ontology.createIndividual(Method, parentUri);

        variable.modelIn(ontology, parentUri, method);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.LocalVariable.individualUri("SomeClass.someMethod.i"));

        assertNotNull(individual);
        assertEquals(LocalVariable.uri(), individual.getOntClass().getURI());
        assertTrue(
                ontology.codeModel().contains(method, ontology.get(definesVariable), individual));
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), "i"));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(hasDeclaredType),
                                type.getIndividual(ontology)));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
    }
}
