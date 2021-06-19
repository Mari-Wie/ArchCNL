package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Parameter;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Test;

public class ConstructorDeclarationVisitorTest
        extends GenericVisitorTest<ConstructorDeclarationVisitor> {

    @Test
    public void testSimpleClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "SimpleClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getConstructors().size());

        final Method constructor = visitor.getConstructors().get(0);

        assertEquals("SimpleClass", constructor.getName());
        assertEquals("SimpleClass(int)", constructor.getSignature());
        assertEquals(Type.UNUSED_VALUE, constructor.getReturnType());
        assertTrue(constructor.getAnnotations().isEmpty());
        assertTrue(constructor.getDeclaredExceptions().isEmpty());
        assertTrue(constructor.getLocalVariables().isEmpty());

        assertEquals(1, constructor.getParameters().size());

        final Parameter param = constructor.getParameters().get(0);

        assertTrue(param.getAnnotations().isEmpty());
        assertTrue(param.getModifiers().isEmpty());
        assertEquals("parameter", param.getName());
        assertEquals("int", param.getType().getName());
        assertTrue(param.getType().isPrimitive());
    }

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "EmptyClass.java");
        unit.accept(visitor, null);

        assertEquals(0, visitor.getConstructors().size());
    }

    @Test
    public void testComplexClassConstructor1()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(2, visitor.getConstructors().size());

        final Method constructor1 = visitor.getConstructors().get(0);

        assertEquals("ComplexClass(double)", constructor1.getSignature());
        assertEquals(1, constructor1.getAnnotations().size());

        assertEquals(1, constructor1.getModifiers().size());
        assertEquals("public", constructor1.getModifiers().get(0).getName());

        final AnnotationInstance annotation = constructor1.getAnnotations().get(0);
        assertEquals("Deprecated", annotation.getName());
        assertEquals(0, annotation.getValues().size());

        assertEquals(1, constructor1.getDeclaredExceptions().size());
        assertEquals("java.lang.Exception", constructor1.getDeclaredExceptions().get(0).getName());
        assertEquals("Exception", constructor1.getDeclaredExceptions().get(0).getSimpleName());
        assertFalse(constructor1.getDeclaredExceptions().get(0).isPrimitive());

        assertTrue(constructor1.getLocalVariables().isEmpty());
        assertTrue(constructor1.getThrownExceptions().isEmpty());

        assertEquals(1, constructor1.getParameters().size());
    }

    @Test
    public void testComplexClassConstructor2()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(2, visitor.getConstructors().size());

        final Method constructor2 = visitor.getConstructors().get(1);

        assertEquals("ComplexClass(double, double)", constructor2.getSignature());
        assertTrue(constructor2.getAnnotations().isEmpty());
        assertTrue(constructor2.getDeclaredExceptions().isEmpty());
        assertTrue(constructor2.getThrownExceptions().isEmpty());

        assertEquals(1, constructor2.getModifiers().size());
        assertEquals("public", constructor2.getModifiers().get(0).getName());

        assertEquals(1, constructor2.getLocalVariables().size());
        final LocalVariable variable = constructor2.getLocalVariables().get(0);

        assertEquals("diameter", variable.getName());
        assertEquals("java.lang.Double", variable.getType().getName());
        assertEquals("Double", variable.getType().getSimpleName());
        assertFalse(variable.getType().isPrimitive());

        assertEquals(2, constructor2.getParameters().size());

        final Parameter param1 = constructor2.getParameters().get(0);
        final Parameter param2 = constructor2.getParameters().get(1);

        assertEquals("radius", param1.getName());
        assertTrue(param1.getAnnotations().isEmpty());
        assertEquals(1, param1.getModifiers().size());
        assertEquals("final", param1.getModifiers().get(0).getName());

        assertEquals("otherHalfOfRadius", param2.getName());
        assertEquals(1, param2.getAnnotations().size());
        final AnnotationInstance param2annotation = param2.getAnnotations().get(0);
        assertEquals("Deprecated", param2annotation.getName());
        assertEquals(1, param2annotation.getValues().size());
        assertEquals("since", param2annotation.getValues().get(0).getName());
        assertEquals("\"yesterday\"", param2annotation.getValues().get(0).getValue());
        assertTrue(param2.getModifiers().isEmpty());
    }

    @Override
    protected Class<ConstructorDeclarationVisitor> getVisitorClass() {
        return ConstructorDeclarationVisitor.class;
    }
}
