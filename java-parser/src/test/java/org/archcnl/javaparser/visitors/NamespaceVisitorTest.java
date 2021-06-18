package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

public class NamespaceVisitorTest extends GenericVisitorTest<NamespaceVisitor> {

	@Test
	public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory
				.getFromPath(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "EmptyClass.java");
		unit.accept(visitor, null);

		final Namespace namespace = visitor.getNamespace();

		assertEquals("examples", namespace.getName());
		assertEquals(Namespace.TOP, namespace.getParent());
	}

	@Test
	public void testClassInSubpackage() throws FileNotFoundException, FileIsNotAJavaClassException {
		final CompilationUnit unit = CompilationUnitFactory.getFromPath(
				GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "subpackage/ClassInSubpackage.java");
		unit.accept(visitor, null);

		final Namespace namespace = visitor.getNamespace();

		assertEquals("examples.subpackage", namespace.getName());
		assertEquals("examples", namespace.getParent().getName());
		assertEquals(Namespace.TOP, namespace.getParent().getParent());
	}

	@Override
	protected Class<NamespaceVisitor> getVisitorClass() {
		return NamespaceVisitor.class;
	}
}
