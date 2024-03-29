package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Field;
import org.junit.Assert;
import org.junit.Test;

public class JavaFieldVisitorTest extends GenericVisitorTest<JavaFieldVisitor> {

    @Test
    public void givenValidJavaClass_whenJavaFieldVisitorVisit_thenFieldsFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.SIMPLE_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(1, visitor.getFields().size());

        final Field field = visitor.getFields().get(0);
        Assert.assertEquals("field", field.getName());
        Assert.assertEquals("int", field.getType().getName());
        Assert.assertTrue(field.getType().isPrimitive());
        Assert.assertEquals(1, field.getModifiers().size());
        Assert.assertEquals("private", field.getModifiers().get(0).getName());
        Assert.assertTrue(field.getAnnotations().isEmpty());
        Assert.assertEquals((Integer) 4, field.getBeginning().get());
    }

    @Test
    public void givenValidEnum_whenJavaFieldVisitorVisit_thenFieldsFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.ENUM_WITH_FIELDS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(1, visitor.getFields().size());

        final Field field = visitor.getFields().get(0);
        Assert.assertEquals("field", field.getName());
        Assert.assertEquals("examples.subpackage.ClassInSubpackage", field.getType().getName());
        Assert.assertEquals("ClassInSubpackage", field.getType().getSimpleName());
        Assert.assertFalse(field.getType().isPrimitive());
        Assert.assertEquals(1, field.getModifiers().size());
        Assert.assertEquals("private", field.getModifiers().get(0).getName());
        Assert.assertTrue(field.getAnnotations().isEmpty());
        Assert.assertEquals((Integer) 8, field.getBeginning().get());
    }

    @Test
    public void givenEmptyJavaClass_whenJavaFieldVisitorVisit_thenNoFieldsFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.EMPTY_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(0, visitor.getFields().size());
    }

    @Test
    public void givenJavaClassWithInnerClass_whenJavaFieldVisitorVisit_thenAllFieldsFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.INNER_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        Assert.assertEquals(3, visitor.getFields().size());

        final Field field1 = visitor.getFields().get(0);
        Assert.assertEquals("field1", field1.getName());
        Assert.assertEquals("examples.ClassWithInnerClass.InnerClass", field1.getType().getName());
        Assert.assertEquals("InnerClass", field1.getType().getSimpleName());
        Assert.assertFalse(field1.getType().isPrimitive());
        Assert.assertEquals((Integer) 4, field1.getBeginning().get());

        final Field field2 = visitor.getFields().get(1);
        Assert.assertEquals("field2", field2.getName());
        Assert.assertEquals("float", field2.getType().getName());
        Assert.assertTrue(field2.getType().isPrimitive());
        Assert.assertEquals((Integer) 5, field2.getBeginning().get());

        final Field field3 = visitor.getFields().get(2);
        Assert.assertEquals("innerField", field3.getName());
        Assert.assertEquals((Integer) 17, field3.getBeginning().get());
    }

    @Override
    protected Class<JavaFieldVisitor> getVisitorClass() {
        return JavaFieldVisitor.class;
    }

    @Override
    protected JavaFieldVisitor createInstance()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException, NoSuchMethodException, SecurityException {
        Object[] paramValues = {Path.of(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES)};
        Class<?>[] paramClasses = {Path.class};
        return getVisitorClass().getDeclaredConstructor(paramClasses).newInstance(paramValues);
    }
}
