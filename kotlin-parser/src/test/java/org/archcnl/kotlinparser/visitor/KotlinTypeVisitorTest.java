package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.junit.jupiter.api.Test;

class KotlinTypeVisitorTest {
    @Test
    void testKotlinTypeOfComplexClass() throws IOException {
        var namedFileContext = TestHelper.getKotlinFileContextFromFile("ComplexClass.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        kotlinTypeVisitor.visit(namedFileContext.getFileContext());

        assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);

        assertEquals("ComplexClass", definedType.getSimpleName());
        assertEquals("example.ComplexClass", definedType.getName());

        assertTrue(definedType instanceof ClassOrInterface);

        ClassOrInterface definedClass = (ClassOrInterface) definedType;

        assertFalse(definedClass.isInterface());

        var methodsOfDefinedClass = definedClass.getMethods();
        assertEquals(4, methodsOfDefinedClass.size());

        var calculateAreaMethod = methodsOfDefinedClass.get(0);
        assertEquals("calculateArea()", calculateAreaMethod.getSignature());

        var stringMethod = methodsOfDefinedClass.get(1);
        assertEquals("stringMethod()", stringMethod.getSignature());

        var referenceMethod = methodsOfDefinedClass.get(2);
        assertEquals("referenceMethod(ClassInSubpackage?)", referenceMethod.getSignature());

        var primitiveMethod = methodsOfDefinedClass.get(3);
        assertEquals("primitiveMethod(Boolean)", primitiveMethod.getSignature());

        var deprecatedAnnotationOfFirstMethod =
                methodsOfDefinedClass.get(0).getAnnotations().get(0);
        assertNotNull(deprecatedAnnotationOfFirstMethod);
        assertEquals("Deprecated", deprecatedAnnotationOfFirstMethod.getName());
        var deprecatedAnnotationValues = deprecatedAnnotationOfFirstMethod.getValues();
        assertEquals(1, deprecatedAnnotationValues.size());
        assertEquals("message", deprecatedAnnotationValues.get(0).getName());
        assertEquals("\"nicht mehr verwenden\"", deprecatedAnnotationValues.get(0).getValue());
    }

    @Test
    void testKotlinTypeOfClassInSubpackage() throws IOException {
        var namedFileContext =
                TestHelper.getKotlinFileContextFromFile("subpackage", "ClassInSubpackage.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        kotlinTypeVisitor.visit(namedFileContext.getFileContext());

        assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);

        assertEquals("ClassInSubpackage", definedType.getSimpleName());
        assertEquals("example.subpackage.ClassInSubpackage", definedType.getName());
    }

    @Test
    void testKotlinTypeOfClassWithInnerClass() throws IOException {
        var namedFileContext = TestHelper.getKotlinFileContextFromFile("ClassWithInnerClass.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        kotlinTypeVisitor.visit(namedFileContext.getFileContext());

        assertEquals(3, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType outerClass = kotlinTypeVisitor.getDefinedTypes().get(0);
        DefinedType innerClass = kotlinTypeVisitor.getDefinedTypes().get(1);
        DefinedType insideClass = kotlinTypeVisitor.getDefinedTypes().get(2);

        assertEquals("ClassWithInnerClass", outerClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass", outerClass.getName());

        assertEquals("InnerClass", innerClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass.InnerClass", innerClass.getName());

        assertEquals("InsideClass", insideClass.getSimpleName());
        assertEquals("example.ClassWithInnerClass.InnerClass.InsideClass", insideClass.getName());
    }

    @Test
    void testKotlinTypeOfInterface() throws IOException {
        var namedFileContext = TestHelper.getKotlinFileContextFromFile("Interface.kt");

        var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        kotlinTypeVisitor.visit(namedFileContext.getFileContext());

        assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

        DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);

        assertEquals("Interface", definedType.getSimpleName());
        assertEquals("example.Interface", definedType.getName());

        assertTrue(definedType instanceof ClassOrInterface);

        ClassOrInterface definedClass = (ClassOrInterface) definedType;

        assertTrue(definedClass.isInterface());

        var methodsOfInterface = definedClass.getMethods();
        assertEquals(5, methodsOfInterface.size());

        var annotationsOfFirstMethod = methodsOfInterface.get(0).getAnnotations();
        assertEquals(0, annotationsOfFirstMethod.size());

        var annotationsOfSecondMethod = methodsOfInterface.get(1).getAnnotations();
        assertEquals(0, annotationsOfSecondMethod.size());

        var annotationsOfThirdMethod = methodsOfInterface.get(2).getAnnotations();
        assertEquals(0, annotationsOfThirdMethod.size());

        var annotationsOfFourthMethod = methodsOfInterface.get(3).getAnnotations();
        assertEquals(1, annotationsOfFourthMethod.size());
        var getAnnotationFourthMethod = annotationsOfFourthMethod.get(0);
        assertEquals("GET", getAnnotationFourthMethod.getName());
        var annotationValuesFourthMethod = getAnnotationFourthMethod.getValues();
        assertEquals(1, annotationValuesFourthMethod.size());
        var firstNameValuePairFourthMethod = annotationValuesFourthMethod.get(0);
        assertEquals("", firstNameValuePairFourthMethod.getName());
        assertEquals("\"/some/url/stuff\"", firstNameValuePairFourthMethod.getValue());

        var annotationsOfFifthMethod = methodsOfInterface.get(4).getAnnotations();
        assertEquals(1, annotationsOfFifthMethod.size());
        var getAnnotationFifthMethod = annotationsOfFifthMethod.get(0);
        assertEquals("GET", getAnnotationFifthMethod.getName());
        var annotationValuesFifthMethod = getAnnotationFifthMethod.getValues();
        assertEquals(1, annotationValuesFifthMethod.size());
        var firstNameValuePairFifthMethod = annotationValuesFifthMethod.get(0);
        assertEquals("", firstNameValuePairFifthMethod.getName());
        assertEquals("\"/some/url/otherStuff/{id}\"", firstNameValuePairFifthMethod.getValue());
    }
}
