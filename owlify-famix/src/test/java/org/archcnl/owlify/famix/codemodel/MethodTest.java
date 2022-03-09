package org.archcnl.owlify.famix.codemodel;

import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.FamixClass;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses.Method;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasModifier;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasName;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.hasSignature;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isConstructor;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixDatatypeProperties.isLocatedAt;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesMethod;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesParameter;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.definesVariable;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasAnnotationInstance;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasCaughtException;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredException;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.hasDeclaredType;
import static org.archcnl.owlify.famix.ontology.FamixOntology.FamixObjectProperties.throwsException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.apache.jena.ontology.Individual;
import org.archcnl.owlify.famix.ontology.FamixOntology;
import org.archcnl.owlify.famix.ontology.FamixOntology.FamixClasses;
import org.junit.Before;
import org.junit.Test;

public class MethodTest {

    private FamixOntology ontology;

    private Parameter param;
    private Type declaredException;
    private AnnotationInstance annotation;
    private Type thrownException;
    private Type caughtException;
    private LocalVariable localVariable;
    private Type returnType;

    private String name;
    private String signature;
    private String parentName;

    private Individual parent;

    private static final Optional<Integer> position1 = Optional.of(5);
    private static final Optional<Integer> position2 = Optional.of(2);
    private static final Path path = Path.of("someRootDirectory/someClassOrInterface");

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntology(
                        new FileInputStream("./src/main/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/main/resources/ontologies/main.owl"));

        initParameters();

        parent = ontology.createIndividual(FamixClass, parentName);
    }

    private void initParameters() {
        name = "method";
        signature = "method(namespace.A)";
        parentName = "namespace.SomeClass";

        param =
                new Parameter(
                        "param",
                        new Type("namespace.A", "A", false),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        path,
                        position1);
        declaredException = new Type("namespace.MyException", "MyException", false);
        annotation = new AnnotationInstance("Deprecated", new ArrayList<>(), path, position1);
        thrownException = new Type("namespace.MyError", "MyError", false);
        caughtException = new Type("java.lang.Exception", "Exception", false);
        localVariable =
                new LocalVariable(
                        new Type("namespace.C", "C", false),
                        "variable",
                        Arrays.asList(new Modifier("public")),
                        path,
                        position1);
        returnType = new Type("namespace.B", "B", false);
    }

    @Test
    public void testMethodTransformation() {
        final boolean constructor = false;

        Method method =
                new Method(
                        name,
                        signature,
                        Arrays.asList(new Modifier("private")),
                        Arrays.asList(param),
                        Arrays.asList(declaredException),
                        Arrays.asList(annotation),
                        returnType,
                        constructor,
                        Arrays.asList(thrownException),
                        Arrays.asList(caughtException),
                        Arrays.asList(localVariable),
                        path,
                        position1);

        method.modelIn(ontology, parentName, parent);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Method.individualUri(parentName + "." + signature));

        assertNotNull(individual);
        assertEquals(Method.uri(), individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasSignature), signature));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(isConstructor), constructor));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual,
                                ontology.get(hasDeclaredType),
                                returnType.getIndividual(ontology)));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual,
                                ontology.get(hasDeclaredException),
                                declaredException.getIndividual(ontology)));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual,
                                ontology.get(hasCaughtException),
                                caughtException.getIndividual(ontology)));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(
                                individual,
                                ontology.get(throwsException),
                                thrownException.getIndividual(ontology)));

        assertTrue(
                ontology.codeModel()
                        .containsLiteral(parent, ontology.get(definesMethod), individual));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));

        // ensure that the components have been modeled (the exact modeling is tested by their unit
        // tests)
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesParameter)));
        assertNotNull(ontology.codeModel().getProperty(individual, ontology.get(definesVariable)));
        assertNotNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));
    }

    @Test
    public void testConstructorTransformation() {
        final boolean constructor = true;

        Method method =
                new Method(
                        name,
                        signature,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Type.UNUSED_VALUE,
                        constructor,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        path,
                        position1);

        method.modelIn(ontology, parentName, parent);

        Individual individual =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Method.individualUri(parentName + "." + signature));

        assertNotNull(individual);
        assertEquals(Method.uri(), individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(individual, ontology.get(hasName), name));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(hasSignature), signature));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, ontology.get(isConstructor), constructor));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));

        // constructors have no return type
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(hasDeclaredType)));

        // no parameters/modifiers/etc. have been set -> there must not be any properties for them
        assertNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasDeclaredException)));
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(hasCaughtException)));
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(throwsException)));
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(hasModifier)));
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(definesParameter)));
        assertNull(ontology.codeModel().getProperty(individual, ontology.get(definesVariable)));
        assertNull(
                ontology.codeModel().getProperty(individual, ontology.get(hasAnnotationInstance)));

        assertTrue(
                ontology.codeModel()
                        .containsLiteral(parent, ontology.get(definesMethod), individual));
    }

    @Test
    public void testOverloadedMethod() {
        final String signature2 = "method(int)";

        final boolean constructor = true;

        Method method1 =
                new Method(
                        name,
                        signature,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Type.UNUSED_VALUE,
                        constructor,
                        Arrays.asList(thrownException),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        path,
                        position1);
        Method method2 =
                new Method(
                        name,
                        signature2,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Type.UNUSED_VALUE,
                        constructor,
                        new ArrayList<>(),
                        Arrays.asList(caughtException),
                        new ArrayList<>(),
                        path,
                        position2);

        method1.modelIn(ontology, parentName, parent);
        method2.modelIn(ontology, parentName, parent);

        Individual individual1 =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Method.individualUri(parentName + "." + signature));
        Individual individual2 =
                ontology.codeModel()
                        .getIndividual(
                                FamixClasses.Method.individualUri(parentName + "." + signature2));

        assertNotNull(individual1);
        assertNotNull(individual2);
        assertEquals(Method.uri(), individual1.getOntClass().getURI());
        assertEquals(Method.uri(), individual2.getOntClass().getURI());
        // same name
        assertTrue(ontology.codeModel().containsLiteral(individual1, ontology.get(hasName), name));
        assertTrue(ontology.codeModel().containsLiteral(individual2, ontology.get(hasName), name));
        // different signature
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual1, ontology.get(hasSignature), signature));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual2, ontology.get(hasSignature), signature2));

        assertTrue(
                ontology.codeModel()
                        .containsLiteral(parent, ontology.get(definesMethod), individual1));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(parent, ontology.get(definesMethod), individual2));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual1,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 5"));
        assertTrue(
                ontology.codeModel()
                        .contains(
                                individual2,
                                ontology.get(isLocatedAt),
                                path.toString() + ", Line: 2"));

        // only individual1 should have a thrown exception
        assertNotNull(ontology.codeModel().getProperty(individual1, ontology.get(throwsException)));
        assertNull(ontology.codeModel().getProperty(individual2, ontology.get(throwsException)));
        // only individual 2 should have a caught exception
        assertNull(ontology.codeModel().getProperty(individual1, ontology.get(hasCaughtException)));
        assertNotNull(
                ontology.codeModel().getProperty(individual2, ontology.get(hasCaughtException)));
    }
}
