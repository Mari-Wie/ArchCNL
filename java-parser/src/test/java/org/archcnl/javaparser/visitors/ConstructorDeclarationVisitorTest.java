package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.AnnotationInstance;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Parameter;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorDeclarationVisitorTest
        extends GenericVisitorTest<ConstructorDeclarationVisitor> {

    @Test
    public void givenValidJavaClass_whenConstructorVisitorClassVisit_thenClassConstructorFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.SIMPLE_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(1, visitor.getConstructors().size());

        final Method constructor = visitor.getConstructors().get(0);
        Assert.assertEquals("SimpleClass", constructor.getName());
        Assert.assertEquals("SimpleClass(int)", constructor.getSignature());
        Assert.assertEquals(Type.UNUSED_VALUE, constructor.getReturnType());
        Assert.assertTrue(constructor.getAnnotations().isEmpty());
        Assert.assertTrue(constructor.getDeclaredExceptions().isEmpty());
        Assert.assertTrue(constructor.getLocalVariables().isEmpty());
        Assert.assertEquals(1, constructor.getParameters().size());
        Assert.assertTrue(
                Path.of(
                                GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES,
                                GenericVisitorTest.SIMPLE_CLASS)
                        .startsWith(constructor.getPath()));
        Assert.assertEquals(6, constructor.getBeginning().get().line);

        final Parameter param = constructor.getParameters().get(0);
        Assert.assertTrue(param.getAnnotations().isEmpty());
        Assert.assertTrue(param.getModifiers().isEmpty());
        Assert.assertEquals("parameter", param.getName());
        Assert.assertEquals("int", param.getType().getName());
        Assert.assertTrue(param.getType().isPrimitive());
        Assert.assertTrue(
                Path.of(
                                GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES,
                                GenericVisitorTest.SIMPLE_CLASS)
                        .startsWith(param.getPath()));
        Assert.assertEquals(6, param.getBeginning().get().line);
    }

    @Test
    public void givenEmptyJavaClass_whenConstructorVisitorClassVisit_thenNoClassConstructorFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.EMPTY_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(0, visitor.getConstructors().size());
    }

    @Test
    public void
            givenJavaClassWithComplexConstructors_whenConstructorVisitorClassVisit_thenFirstClassConstructorFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.COMPLEX_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(2, visitor.getConstructors().size());

        final Method constructor1 = visitor.getConstructors().get(0);
        Assert.assertEquals("ComplexClass(double)", constructor1.getSignature());
        Assert.assertEquals(1, constructor1.getAnnotations().size());
        Assert.assertEquals(1, constructor1.getModifiers().size());
        Assert.assertEquals("public", constructor1.getModifiers().get(0).getName());
        Assert.assertEquals(1, constructor1.getDeclaredExceptions().size());
        Assert.assertEquals(
                "java.lang.Exception", constructor1.getDeclaredExceptions().get(0).getName());
        Assert.assertEquals(
                "Exception", constructor1.getDeclaredExceptions().get(0).getSimpleName());
        Assert.assertFalse(constructor1.getDeclaredExceptions().get(0).isPrimitive());
        Assert.assertTrue(constructor1.getLocalVariables().isEmpty());
        Assert.assertTrue(constructor1.getThrownExceptions().isEmpty());
        Assert.assertEquals(1, constructor1.getParameters().size());
        Assert.assertEquals(18, constructor1.getParameters().get(0).getBeginning().get().line);
        Assert.assertTrue(
                Path.of(
                                GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES,
                                GenericVisitorTest.SIMPLE_CLASS)
                        .startsWith(constructor1.getPath()));

        final AnnotationInstance annotation = constructor1.getAnnotations().get(0);
        Assert.assertEquals("Deprecated", annotation.getName());
        Assert.assertEquals(0, annotation.getValues().size());
        Assert.assertEquals(17, annotation.getBeginning().get().line);
    }

    @Test
    public void
            givenJavaClassWithComplexConstructors_whenConstructorVisitorClassVisit_thenSecondClassConstructorFound()
                    throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.COMPLEX_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(2, visitor.getConstructors().size());

        final Method constructor2 = visitor.getConstructors().get(1);
        Assert.assertEquals("ComplexClass(double, double)", constructor2.getSignature());
        Assert.assertTrue(constructor2.getAnnotations().isEmpty());
        Assert.assertTrue(constructor2.getDeclaredExceptions().isEmpty());
        Assert.assertTrue(constructor2.getThrownExceptions().isEmpty());
        Assert.assertEquals(1, constructor2.getModifiers().size());
        Assert.assertEquals("public", constructor2.getModifiers().get(0).getName());
        Assert.assertEquals(1, constructor2.getLocalVariables().size());

        final LocalVariable variable = constructor2.getLocalVariables().get(0);
        Assert.assertEquals("diameter", variable.getName());
        Assert.assertEquals("java.lang.Double", variable.getType().getName());
        Assert.assertEquals("Double", variable.getType().getSimpleName());
        Assert.assertFalse(variable.getType().isPrimitive());
        Assert.assertEquals(25, variable.getBeginning().get().line);
        Assert.assertEquals(2, constructor2.getParameters().size());

        final Parameter param1 = constructor2.getParameters().get(0);
        Assert.assertEquals("radius", param1.getName());
        Assert.assertTrue(param1.getAnnotations().isEmpty());
        Assert.assertEquals(1, param1.getModifiers().size());
        Assert.assertEquals("final", param1.getModifiers().get(0).getName());
        Assert.assertEquals(24, param1.getBeginning().get().line);

        final Parameter param2 = constructor2.getParameters().get(1);
        Assert.assertEquals("otherHalfOfRadius", param2.getName());
        Assert.assertEquals(1, param2.getAnnotations().size());
        Assert.assertTrue(param2.getModifiers().isEmpty());
        Assert.assertEquals(24, param2.getBeginning().get().line);

        final AnnotationInstance param2annotation = param2.getAnnotations().get(0);
        Assert.assertEquals("Deprecated", param2annotation.getName());
        Assert.assertEquals(1, param2annotation.getValues().size());
        Assert.assertEquals("since", param2annotation.getValues().get(0).getName());
        Assert.assertEquals("\"yesterday\"", param2annotation.getValues().get(0).getValue());
        Assert.assertEquals(24, param2annotation.getBeginning().get().line);

        Assert.assertTrue(
                Path.of(
                                GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES,
                                GenericVisitorTest.SIMPLE_CLASS)
                        .startsWith(constructor2.getPath()));
    }

    @Override
    protected Class<ConstructorDeclarationVisitor> getVisitorClass() {
        return ConstructorDeclarationVisitor.class;
    }

    @Override
    protected ConstructorDeclarationVisitor createInstance()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException, NoSuchMethodException, SecurityException {
        Object[] paramValues = {Path.of(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES)};
        Class<?>[] paramClasses = {Path.class};
        return getVisitorClass().getDeclaredConstructor(paramClasses).newInstance(paramValues);
    }
}
