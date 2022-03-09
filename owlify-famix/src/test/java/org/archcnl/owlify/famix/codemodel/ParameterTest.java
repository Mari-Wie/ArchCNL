package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Method;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesParameter;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.Position;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class ParameterTest {

    private FamixOntology ontology;
    private static final Optional<Position> position = Optional.of(new Position(5, 4));
    private static final Path path = Path.of("someRootDirectory/someClassOrInterface");

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));
    }

    @Test
    public void testParameter() {
        final String name = "parameter";
        final String parentName = "namespace.Class.method";
        final Type type = new Type("boolean", "boolean", true);
        final List<Modifier> modifiers = Arrays.asList(new Modifier("final"));
        final List<AnnotationInstance> annotations =
                Arrays.asList(
                        new AnnotationInstance("Deprecated", new ArrayList<>(), path, position));

        Parameter param = new Parameter(name, type, modifiers, annotations, path, position);
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
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }
}
