package org.archcnl.javaparser.visitors;

import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Method;
import org.junit.Assert;
import org.junit.Test;
import com.github.javaparser.ast.CompilationUnit;

public class MethodDeclarationVisitorTest extends GenericVisitorTest<MethodDeclarationVisitor> {

  @Test
  public void givenEmptyJavaClass_whenMethodDeclarationVisitorVisit_thenMethodsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.EMPTY_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertTrue(visitor.getMethods().isEmpty());
  }

  @Test
  public void givenValidEnum_whenMethodDeclarationVisitorVisit_thenMethodsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.ENUM);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getMethods().size());

    final Method method = visitor.getMethods().get(0);
    Assert.assertEquals("getIndex(Enumeration)", method.getSignature());
    Assert.assertEquals("getIndex", method.getName());
    Assert.assertEquals("int", method.getReturnType().getName());
    Assert.assertTrue(method.getReturnType().isPrimitive());
    Assert.assertEquals(0, method.getAnnotations().size());
    Assert.assertEquals(0, method.getDeclaredExceptions().size());
    Assert.assertEquals(0, method.getThrownExceptions().size());
    Assert.assertEquals(0, method.getLocalVariables().size());
    Assert.assertEquals(1, method.getParameters().size());
    Assert.assertEquals("e", method.getParameters().get(0).getName());
    Assert.assertEquals("examples.Enumeration", method.getParameters().get(0).getType().getName());
    Assert.assertEquals("Enumeration", method.getParameters().get(0).getType().getSimpleName());
    Assert.assertEquals(0, method.getParameters().get(0).getModifiers().size());
    Assert.assertEquals(0, method.getParameters().get(0).getAnnotations().size());
    Assert.assertEquals(2, method.getModifiers().size());
    Assert.assertEquals("public", method.getModifiers().get(0).getName());
    Assert.assertEquals("static", method.getModifiers().get(1).getName());
  }

  @Test
  public void givenValidJavaClass_whenMethodDeclarationVisitorVisit_thenMethodsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.SIMPLE_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getMethods().size());

    final Method method = visitor.getMethods().get(0);
    Assert.assertEquals("get()", method.getSignature());
    Assert.assertEquals("get", method.getName());
    Assert.assertEquals("int", method.getReturnType().getName());
    Assert.assertTrue(method.getReturnType().isPrimitive());
    Assert.assertEquals(0, method.getAnnotations().size());
    Assert.assertEquals(0, method.getDeclaredExceptions().size());
    Assert.assertEquals(0, method.getThrownExceptions().size());
    Assert.assertEquals(0, method.getLocalVariables().size());
    Assert.assertEquals(0, method.getParameters().size());
    Assert.assertEquals(1, method.getModifiers().size());
    Assert.assertEquals("public", method.getModifiers().get(0).getName());
  }

  @Test
  public void givenValidComplexClass_whenMethodDeclarationVisitorVisit_thenMethodsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.COMPLEX_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(4, visitor.getMethods().size());

    final Method method1 = visitor.getMethods().get(0);
    Assert.assertEquals("calculateArea()", method1.getSignature());

    final Method method2 = visitor.getMethods().get(1);
    Assert.assertEquals("stringMethod()", method2.getSignature());
    Assert.assertEquals(2, method2.getAnnotations().size());
    Assert.assertEquals("Override", method2.getAnnotations().get(0).getName());
    Assert.assertEquals(0, method2.getAnnotations().get(0).getValues().size());
    Assert.assertEquals("SuppressWarnings", method2.getAnnotations().get(1).getName());
    Assert.assertEquals(0, method2.getAnnotations().get(1).getValues().size());
    Assert.assertEquals(0, method2.getLocalVariables().size());

    final Method method3 = visitor.getMethods().get(2);
    Assert.assertEquals("referenceMethod(ClassInSubpackage)", method3.getSignature());
    Assert.assertEquals(1, method3.getParameters().size());
    Assert.assertEquals("parameter", method3.getParameters().get(0).getName());
    Assert.assertEquals("examples.subpackage.ClassInSubpackage",
        method3.getParameters().get(0).getType().getName());
    Assert.assertEquals("examples.SimpleClass", method3.getReturnType().getName());
    Assert.assertEquals(2, method3.getAnnotations().size());
    final var deprecationAnnotationMethod3 = method3.getAnnotations().get(1);
    Assert.assertEquals("Deprecated", deprecationAnnotationMethod3.getName());
    final var sinceNeverValuePairMethod3 = deprecationAnnotationMethod3.getValues().get(0);
    Assert.assertEquals("since", sinceNeverValuePairMethod3.getName());
    Assert.assertEquals("\"neverEver\"", sinceNeverValuePairMethod3.getValue());

    final Method method4 = visitor.getMethods().get(3);
    Assert.assertEquals("primitiveMethod(boolean)", method4.getSignature());
    Assert.assertEquals(1, method4.getLocalVariables().size());
    Assert.assertEquals("characters", method4.getLocalVariables().get(0).getName());
    Assert.assertEquals("java.util.List", method4.getLocalVariables().get(0).getType().getName());
    Assert.assertFalse(method4.getLocalVariables().get(0).getType().isPrimitive());
    Assert.assertEquals(2, method4.getAnnotations().size());
    final var deprecationAnnotationMethod4 = method4.getAnnotations().get(1);
    Assert.assertEquals("Deprecated", deprecationAnnotationMethod4.getName());
    final var sinceNeverValuePairMethod4 = deprecationAnnotationMethod4.getValues().get(0);
    Assert.assertEquals("since", sinceNeverValuePairMethod4.getName());
    Assert.assertEquals("\"never\"", sinceNeverValuePairMethod4.getValue());
  }

  @Override
  protected Class<MethodDeclarationVisitor> getVisitorClass() {
    return MethodDeclarationVisitor.class;
  }
}
