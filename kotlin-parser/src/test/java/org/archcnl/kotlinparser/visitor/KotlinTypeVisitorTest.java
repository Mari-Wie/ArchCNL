package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KotlinTypeVisitorTest {

  @Test
  void givenKotlinClass_whenKotlinTypeVisitorVisit_thenTypeFound() throws IOException {
    // given
    final var namedFileContext = TestHelper.getKotlinFileContextFromFile(TestHelper.COMPLEX_CLASS);
    final var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
    // when
    kotlinTypeVisitor.visit(namedFileContext.getFileContext());
    // then
    Assertions.assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

    final DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);
    Assertions.assertEquals("ComplexClass", definedType.getSimpleName());
    Assertions.assertEquals("example.ComplexClass", definedType.getName());
    Assertions.assertTrue(definedType instanceof ClassOrInterface);

    final ClassOrInterface definedClass = (ClassOrInterface) definedType;
    Assertions.assertFalse(definedClass.isInterface());

    final var methodsOfDefinedClass = definedClass.getMethods();
    Assertions.assertEquals(4, methodsOfDefinedClass.size());

    final var calculateAreaMethod = methodsOfDefinedClass.get(0);
    Assertions.assertEquals("calculateArea()", calculateAreaMethod.getSignature());

    final var stringMethod = methodsOfDefinedClass.get(1);
    Assertions.assertEquals("stringMethod()", stringMethod.getSignature());

    final var referenceMethod = methodsOfDefinedClass.get(2);
    Assertions.assertEquals("referenceMethod(ClassInSubpackage?)", referenceMethod.getSignature());

    final var primitiveMethod = methodsOfDefinedClass.get(3);
    Assertions.assertEquals("primitiveMethod(Boolean)", primitiveMethod.getSignature());

    final var deprecatedAnnotationOfFirstMethod =
        methodsOfDefinedClass.get(0).getAnnotations().get(0);
    Assertions.assertNotNull(deprecatedAnnotationOfFirstMethod);
    Assertions.assertEquals("Deprecated", deprecatedAnnotationOfFirstMethod.getName());

    final var deprecatedAnnotationValues = deprecatedAnnotationOfFirstMethod.getValues();
    Assertions.assertEquals(1, deprecatedAnnotationValues.size());
    Assertions.assertEquals("message", deprecatedAnnotationValues.get(0).getName());
    Assertions.assertEquals("\"nicht mehr verwenden\"",
        deprecatedAnnotationValues.get(0).getValue());
  }

  @Test
  void givenKotlinClassInSubpackage_whenKotlinTypeVisitorVisit_thenTypeFound() throws IOException {
    // given
    final var namedFileContext = TestHelper.getKotlinFileContextFromFile(TestHelper.SUBPACKAGE,
        TestHelper.CLASS_IN_SUBPACKAGE);
    final var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
    // when
    kotlinTypeVisitor.visit(namedFileContext.getFileContext());
    // then
    Assertions.assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

    final DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);
    Assertions.assertEquals("ClassInSubpackage", definedType.getSimpleName());
    Assertions.assertEquals("example.subpackage.ClassInSubpackage", definedType.getName());
  }

  @Test
  void givenKotlinClassWithInnerClass_whenKotlinTypeVisitorVisit_thenAllTypesFound()
      throws IOException {
    // given
    final var namedFileContext =
        TestHelper.getKotlinFileContextFromFile(TestHelper.CLASS_WITH_INNER_CLASS);
    final var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
    // when
    kotlinTypeVisitor.visit(namedFileContext.getFileContext());
    // then
    Assertions.assertEquals(3, kotlinTypeVisitor.getDefinedTypes().size());

    final DefinedType outerClass = kotlinTypeVisitor.getDefinedTypes().get(0);
    Assertions.assertEquals("ClassWithInnerClass", outerClass.getSimpleName());
    Assertions.assertEquals("example.ClassWithInnerClass", outerClass.getName());

    final DefinedType innerClass = kotlinTypeVisitor.getDefinedTypes().get(1);
    Assertions.assertEquals("InnerClass", innerClass.getSimpleName());
    Assertions.assertEquals("example.ClassWithInnerClass.InnerClass", innerClass.getName());

    final DefinedType insideClass = kotlinTypeVisitor.getDefinedTypes().get(2);
    Assertions.assertEquals("InsideClass", insideClass.getSimpleName());
    Assertions.assertEquals("example.ClassWithInnerClass.InnerClass.InsideClass",
        insideClass.getName());
  }

  @Test
  void givenKotlinInterface_whenKotlinTypeVisitorVisit_thenTypeFound() throws IOException {
    // given
    final var namedFileContext = TestHelper.getKotlinFileContextFromFile(TestHelper.INTERFACE);
    final var kotlinTypeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
    // when
    kotlinTypeVisitor.visit(namedFileContext.getFileContext());
    // then
    Assertions.assertEquals(1, kotlinTypeVisitor.getDefinedTypes().size());

    final DefinedType definedType = kotlinTypeVisitor.getDefinedTypes().get(0);
    Assertions.assertEquals("Interface", definedType.getSimpleName());
    Assertions.assertEquals("example.Interface", definedType.getName());
    Assertions.assertTrue(definedType instanceof ClassOrInterface);

    final ClassOrInterface definedClass = (ClassOrInterface) definedType;
    Assertions.assertTrue(definedClass.isInterface());

    final var methodsOfInterface = definedClass.getMethods();
    Assertions.assertEquals(5, methodsOfInterface.size());

    final var annotationsOfFirstMethod = methodsOfInterface.get(0).getAnnotations();
    Assertions.assertEquals(0, annotationsOfFirstMethod.size());

    final var annotationsOfSecondMethod = methodsOfInterface.get(1).getAnnotations();
    Assertions.assertEquals(0, annotationsOfSecondMethod.size());

    final var annotationsOfThirdMethod = methodsOfInterface.get(2).getAnnotations();
    Assertions.assertEquals(0, annotationsOfThirdMethod.size());

    final var annotationsOfFourthMethod = methodsOfInterface.get(3).getAnnotations();
    Assertions.assertEquals(1, annotationsOfFourthMethod.size());
    final var getAnnotationFourthMethod = annotationsOfFourthMethod.get(0);
    Assertions.assertEquals("GET", getAnnotationFourthMethod.getName());
    final var annotationValuesFourthMethod = getAnnotationFourthMethod.getValues();
    Assertions.assertEquals(1, annotationValuesFourthMethod.size());
    final var firstNameValuePairFourthMethod = annotationValuesFourthMethod.get(0);
    Assertions.assertEquals("", firstNameValuePairFourthMethod.getName());
    Assertions.assertEquals("\"/some/url/stuff\"", firstNameValuePairFourthMethod.getValue());

    final var annotationsOfFifthMethod = methodsOfInterface.get(4).getAnnotations();
    Assertions.assertEquals(1, annotationsOfFifthMethod.size());
    final var getAnnotationFifthMethod = annotationsOfFifthMethod.get(0);
    Assertions.assertEquals("GET", getAnnotationFifthMethod.getName());
    final var annotationValuesFifthMethod = getAnnotationFifthMethod.getValues();
    Assertions.assertEquals(1, annotationValuesFifthMethod.size());
    final var firstNameValuePairFifthMethod = annotationValuesFifthMethod.get(0);
    Assertions.assertEquals("", firstNameValuePairFifthMethod.getName());
    Assertions.assertEquals("\"/some/url/otherStuff/{id}\"",
        firstNameValuePairFifthMethod.getValue());
  }
}
