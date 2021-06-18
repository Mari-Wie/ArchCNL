package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Field;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

public class JavaFieldVisitorTest extends GenericVisitorTest<JavaFieldVisitor> {

	@Test
	public void testFieldVisiting() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory
				.getFromPath(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "SimpleClass.java");
		unit.accept(visitor, null);

		assertEquals(1, visitor.getFields().size());

		final Field field = visitor.getFields().get(0);

		assertEquals("field", field.getName());
		assertEquals("int", field.getType().getName());
		assertTrue(field.getType().isPrimitive());
		assertEquals(1, field.getModifiers().size());
		assertEquals("private", field.getModifiers().get(0).getName());
		assertTrue(field.getAnnotations().isEmpty());
	}

	@Test
	public void testFieldReferenceType() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory
				.getFromPath(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "EnumWithField.java");
		unit.accept(visitor, null);

		assertEquals(1, visitor.getFields().size());

		final Field field = visitor.getFields().get(0);

		assertEquals("field", field.getName());
		assertEquals("examples.subpackage.ClassInSubpackage", field.getType().getName());
		assertEquals("ClassInSubpackage", field.getType().getSimpleName());
		assertFalse(field.getType().isPrimitive());
		assertEquals(1, field.getModifiers().size());
		assertEquals("private", field.getModifiers().get(0).getName());
		assertTrue(field.getAnnotations().isEmpty());
	}

	@Test
	public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory
				.getFromPath(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "EmptyClass.java");
		unit.accept(visitor, null);

		assertEquals(0, visitor.getFields().size());
	}

	@Test
	public void testNestedClass() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory
				.getFromPath(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "ClassWithInnerClass.java");
		unit.accept(visitor, null);

		assertEquals(3, visitor.getFields().size());

		final Field field1 = visitor.getFields().get(0);
		final Field field2 = visitor.getFields().get(1);
		final Field field3 = visitor.getFields().get(2);

		assertEquals("field1", field1.getName());
		assertEquals("field2", field2.getName());
		assertEquals("innerField", field3.getName());

		assertEquals("examples.ClassWithInnerClass.InnerClass", field1.getType().getName());
		assertEquals("InnerClass", field1.getType().getSimpleName());
		assertEquals("float", field2.getType().getName());

		assertFalse(field1.getType().isPrimitive());
		assertTrue(field2.getType().isPrimitive());
	}

	@Override
	protected Class<JavaFieldVisitor> getVisitorClass() {
		return JavaFieldVisitor.class;
	}
}
