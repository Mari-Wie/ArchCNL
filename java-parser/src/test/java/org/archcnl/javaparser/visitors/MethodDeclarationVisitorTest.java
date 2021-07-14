package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Method;
import org.junit.Test;

public class MethodDeclarationVisitorTest extends GenericVisitorTest<MethodDeclarationVisitor> {

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "EmptyClass.java");
        unit.accept(visitor, null);

        assertTrue(visitor.getMethods().isEmpty());
    }

    @Test
    public void testEnumeration() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "Enumeration.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getMethods().size());

        final Method method = visitor.getMethods().get(0);

        assertEquals("getIndex(Enumeration)", method.getSignature());
        assertEquals("getIndex", method.getName());
        assertEquals("int", method.getReturnType().getName());
        assertTrue(method.getReturnType().isPrimitive());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getDeclaredExceptions().size());
        assertEquals(0, method.getThrownExceptions().size());
        assertEquals(0, method.getLocalVariables().size());
        assertEquals(1, method.getParameters().size());
        assertEquals("e", method.getParameters().get(0).getName());
        assertEquals("examples.Enumeration", method.getParameters().get(0).getType().getName());
        assertEquals("Enumeration", method.getParameters().get(0).getType().getSimpleName());
        assertEquals(0, method.getParameters().get(0).getModifiers().size());
        assertEquals(0, method.getParameters().get(0).getAnnotations().size());
        assertEquals(2, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
        assertEquals("static", method.getModifiers().get(1).getName());
    }

    @Test
    public void testSimpleClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "SimpleClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getMethods().size());

        final Method method = visitor.getMethods().get(0);

        assertEquals("get()", method.getSignature());
        assertEquals("get", method.getName());
        assertEquals("int", method.getReturnType().getName());
        assertTrue(method.getReturnType().isPrimitive());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getDeclaredExceptions().size());
        assertEquals(0, method.getThrownExceptions().size());
        assertEquals(0, method.getLocalVariables().size());
        assertEquals(0, method.getParameters().size());
        assertEquals(1, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
    }

    @Test
    public void testComplexClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(4, visitor.getMethods().size());

        final Method method1 = visitor.getMethods().get(0);
        final Method method2 = visitor.getMethods().get(1);
        final Method method3 = visitor.getMethods().get(2);
        final Method method4 = visitor.getMethods().get(3);

        assertEquals("calculateArea()", method1.getSignature());
        assertEquals("stringMethod()", method2.getSignature());
        assertEquals("referenceMethod(ClassInSubpackage)", method3.getSignature());
        assertEquals("primitiveMethod(boolean)", method4.getSignature());

        assertEquals(2, method2.getAnnotations().size());
        assertEquals("Override", method2.getAnnotations().get(0).getName());
        assertEquals(0, method2.getAnnotations().get(0).getValues().size());
        assertEquals("SuppressWarnings", method2.getAnnotations().get(1).getName());
        assertEquals(0, method2.getAnnotations().get(1).getValues().size());
        assertEquals(0, method2.getLocalVariables().size());

        assertEquals(1, method3.getParameters().size());
        assertEquals("parameter", method3.getParameters().get(0).getName());
        assertEquals(
                "examples.subpackage.ClassInSubpackage",
                method3.getParameters().get(0).getType().getName());
        assertEquals("examples.SimpleClass", method3.getReturnType().getName());
        assertEquals(2, method3.getAnnotations().size());
        final var deprecationAnnotationMethod3 = method3.getAnnotations().get(1);
        assertEquals("Deprecated", deprecationAnnotationMethod3.getName());
        final var sinceNeverValuePairMethod3 = deprecationAnnotationMethod3.getValues().get(0);
        assertEquals("since", sinceNeverValuePairMethod3.getName());
        assertEquals("\"neverEver\"", sinceNeverValuePairMethod3.getValue());

        assertEquals(1, method4.getLocalVariables().size());
        assertEquals("characters", method4.getLocalVariables().get(0).getName());
        assertEquals("java.util.List", method4.getLocalVariables().get(0).getType().getName());
        assertFalse(method4.getLocalVariables().get(0).getType().isPrimitive());
        assertEquals(2, method4.getAnnotations().size());
        final var multipleValueAnnotationMethod4 = method4.getAnnotations().get(1);
        assertEquals("FictiveAnnotation", multipleValueAnnotationMethod4.getName());
        final var intValuePairMethod4 = multipleValueAnnotationMethod4.getValues().get(0);
        assertEquals("intValue", intValuePairMethod4.getName());
        assertEquals("0", intValuePairMethod4.getValue());
        final var doubleValuePairMethod4 = multipleValueAnnotationMethod4.getValues().get(1);
        assertEquals("doubleValue", doubleValuePairMethod4.getName());
        assertEquals("3.14", doubleValuePairMethod4.getValue());
    }

    @Override
    protected Class<MethodDeclarationVisitor> getVisitorClass() {
        return MethodDeclarationVisitor.class;
    }
}
