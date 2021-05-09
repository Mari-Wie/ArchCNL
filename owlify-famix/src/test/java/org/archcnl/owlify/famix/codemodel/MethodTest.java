package org.archcnl.owlify.famix.codemodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Property;
import org.archcnl.owlify.famix.ontology.FamixOntologyNew;
import org.archcnl.owlify.famix.ontology.FamixURIs;
import org.junit.Before;
import org.junit.Test;

public class MethodTest {

    private FamixOntologyNew ontology;

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
    private Individual paramType;
    private Individual declaredExceptionType;
    private Individual annotationType;
    private Individual returnTypeType;
    private Individual localVariableType;
    private Individual thrownExceptionType;
    private Individual caughtExceptionType;

    @Before
    public void setUp() throws FileNotFoundException {
        ontology =
                new FamixOntologyNew(
                        new FileInputStream("./src/test/resources/ontologies/famix.owl"),
                        new FileInputStream("./src/test/resources/ontologies/main.owl"));

        initParameters();
        createTestIndividuals();
        addTypesToCache();
    }

    private void initParameters() {
        name = "method";
        signature = "method(namespace.A)";
        parentName = "namespace.SomeClass";

        param =
                new Parameter(
                        "param",
                        new Type("namespace.A", false),
                        new ArrayList<>(),
                        new ArrayList<>());
        declaredException = new Type("namespace.MyException", false);
        annotation = new AnnotationInstance("Deprecated", new ArrayList<>());
        thrownException = new Type("namespace.MyError", false);
        caughtException = new Type("java.lang.Exception", false);
        localVariable = new LocalVariable(new Type("namespace.C", false), "variable");
        returnType = new Type("namespace.B", false);
    }

    private void addTypesToCache() {
        ontology.typeCache().addDefinedType(param.getType().getName(), paramType);
        ontology.typeCache().addDefinedType(declaredException.getName(), declaredExceptionType);
        ontology.typeCache().addDefinedType(caughtException.getName(), caughtExceptionType);
        ontology.typeCache().addDefinedType(thrownException.getName(), thrownExceptionType);
        ontology.typeCache().addDefinedType(annotation.getName(), annotationType);
        ontology.typeCache().addDefinedType(returnType.getName(), returnTypeType);
        ontology.typeCache().addDefinedType(localVariable.getType().getName(), localVariableType);
    }

    private void createTestIndividuals() {
        parent =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(parentName);
        paramType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(param.getType().getName());
        declaredExceptionType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(declaredException.getName());
        annotationType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.ANNOTATION_TYPE)
                        .createIndividual(annotation.getName());
        returnTypeType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(returnType.getName());
        localVariableType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(localVariable.getType().getName());
        thrownExceptionType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(thrownException.getName());
        caughtExceptionType =
                ontology.codeModel()
                        .getOntClass(FamixURIs.FAMIX_CLASS)
                        .createIndividual(caughtException.getName());
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
                        Arrays.asList(localVariable));

        method.modelIn(ontology, parent);

        Individual individual = ontology.codeModel().getIndividual(parentName + "." + signature);
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasSignature = ontology.codeModel().getProperty(FamixURIs.HAS_SIGNATURE);
        Property definedMethod = ontology.codeModel().getProperty(FamixURIs.DEFINES_METHOD);
        Property isConstructor = ontology.codeModel().getProperty(FamixURIs.IS_CONSTRUCTOR);
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property definesParameter = ontology.codeModel().getProperty(FamixURIs.DEFINES_PARAMETER);
        Property definesVariable = ontology.codeModel().getProperty(FamixURIs.DEFINES_VARIABLE);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);
        Property hasDeclaredException =
                ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_EXCEPTION);
        Property hasCaughtException =
                ontology.codeModel().getProperty(FamixURIs.HAS_CAUGHT_EXCEPTION);
        Property throwsException = ontology.codeModel().getProperty(FamixURIs.THROWS_EXCEPTION);

        assertNotNull(individual);
        assertEquals(FamixURIs.METHOD, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasSignature, signature));
        assertTrue(ontology.codeModel().containsLiteral(individual, isConstructor, constructor));
        assertTrue(
                ontology.codeModel().containsLiteral(individual, hasDeclaredType, returnTypeType));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, hasDeclaredException, declaredExceptionType));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, hasCaughtException, caughtExceptionType));
        assertTrue(
                ontology.codeModel()
                        .containsLiteral(individual, throwsException, thrownExceptionType));

        assertTrue(ontology.codeModel().containsLiteral(parent, definedMethod, individual));

        // ensure that the components have been modeled (the exact modeling is tested by their unit
        // tests)
        assertNotNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNotNull(ontology.codeModel().getProperty(individual, definesParameter));
        assertNotNull(ontology.codeModel().getProperty(individual, definesVariable));
        assertNotNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));
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
                        new ArrayList<>());

        method.modelIn(ontology, parent);

        Individual individual = ontology.codeModel().getIndividual(parentName + "." + signature);
        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasSignature = ontology.codeModel().getProperty(FamixURIs.HAS_SIGNATURE);
        Property definedMethod = ontology.codeModel().getProperty(FamixURIs.DEFINES_METHOD);
        Property isConstructor = ontology.codeModel().getProperty(FamixURIs.IS_CONSTRUCTOR);
        Property hasDeclaredType = ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_TYPE);
        Property hasModifier = ontology.codeModel().getProperty(FamixURIs.HAS_MODIFIER);
        Property definesParameter = ontology.codeModel().getProperty(FamixURIs.DEFINES_PARAMETER);
        Property definesVariable = ontology.codeModel().getProperty(FamixURIs.DEFINES_VARIABLE);
        Property hasAnnotationInstance =
                ontology.codeModel().getProperty(FamixURIs.HAS_ANNOTATION_INSTANCE);
        Property hasDeclaredException =
                ontology.codeModel().getProperty(FamixURIs.HAS_DECLARED_EXCEPTION);
        Property hasCaughtException =
                ontology.codeModel().getProperty(FamixURIs.HAS_CAUGHT_EXCEPTION);
        Property throwsException = ontology.codeModel().getProperty(FamixURIs.THROWS_EXCEPTION);

        assertNotNull(individual);
        assertEquals(FamixURIs.METHOD, individual.getOntClass().getURI());
        assertTrue(ontology.codeModel().containsLiteral(individual, hasName, name));
        assertTrue(ontology.codeModel().containsLiteral(individual, hasSignature, signature));
        assertTrue(ontology.codeModel().containsLiteral(individual, isConstructor, constructor));

        // constructors have no return type
        assertNull(ontology.codeModel().getProperty(individual, hasDeclaredType));

        // no parameters/modifiers/etc. have been set -> there must not be any properties for them
        assertNull(ontology.codeModel().getProperty(individual, hasDeclaredException));
        assertNull(ontology.codeModel().getProperty(individual, hasCaughtException));
        assertNull(ontology.codeModel().getProperty(individual, throwsException));
        assertNull(ontology.codeModel().getProperty(individual, hasModifier));
        assertNull(ontology.codeModel().getProperty(individual, definesParameter));
        assertNull(ontology.codeModel().getProperty(individual, definesVariable));
        assertNull(ontology.codeModel().getProperty(individual, hasAnnotationInstance));

        assertTrue(ontology.codeModel().containsLiteral(parent, definedMethod, individual));
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
                        new ArrayList<>());
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
                        new ArrayList<>());

        method1.modelIn(ontology, parent);
        method2.modelIn(ontology, parent);

        Individual individual1 = ontology.codeModel().getIndividual(parentName + "." + signature);
        Individual individual2 = ontology.codeModel().getIndividual(parentName + "." + signature2);

        Property hasName = ontology.codeModel().getProperty(FamixURIs.HAS_NAME);
        Property hasSignature = ontology.codeModel().getProperty(FamixURIs.HAS_SIGNATURE);
        Property definedMethod = ontology.codeModel().getProperty(FamixURIs.DEFINES_METHOD);
        Property hasCaughtException =
                ontology.codeModel().getProperty(FamixURIs.HAS_CAUGHT_EXCEPTION);
        Property throwsException = ontology.codeModel().getProperty(FamixURIs.THROWS_EXCEPTION);

        assertNotNull(individual1);
        assertNotNull(individual2);
        assertEquals(FamixURIs.METHOD, individual1.getOntClass().getURI());
        assertEquals(FamixURIs.METHOD, individual2.getOntClass().getURI());
        // same name
        assertTrue(ontology.codeModel().containsLiteral(individual1, hasName, name));
        assertTrue(ontology.codeModel().containsLiteral(individual2, hasName, name));
        // different signature
        assertTrue(ontology.codeModel().containsLiteral(individual1, hasSignature, signature));
        assertTrue(ontology.codeModel().containsLiteral(individual2, hasSignature, signature2));

        assertTrue(ontology.codeModel().containsLiteral(parent, definedMethod, individual1));
        assertTrue(ontology.codeModel().containsLiteral(parent, definedMethod, individual2));

        // only individual1 should have a thrown exception
        assertNotNull(ontology.codeModel().getProperty(individual1, throwsException));
        assertNull(ontology.codeModel().getProperty(individual2, throwsException));
        // only individual 2 should have a caught exception
        assertNull(ontology.codeModel().getProperty(individual1, hasCaughtException));
        assertNotNull(ontology.codeModel().getProperty(individual2, hasCaughtException));
    }
}
