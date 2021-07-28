package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.junit.Assert;
import org.junit.Test;

public class NamespaceVisitorTest extends GenericVisitorTest<NamespaceVisitor> {

    @Test
    public void givenEmptyClass_whenNamespaceVisitorVisit_thenNamespaceFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + GenericVisitorTest.EMPTY_CLASS);
        // when
        unit.accept(visitor, null);
        // then
        final Namespace namespace = visitor.getNamespace();
        Assert.assertEquals("examples", namespace.getName());
        Assert.assertEquals(Namespace.TOP, namespace.getParent());
    }

    @Test
    public void givenClassInSubpackage_whenNamespaceVisitorVisit_thenNamespaceFound()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                                + "subpackage/ClassInSubpackage.java");
        // when
        unit.accept(visitor, null);
        // then
        final Namespace namespace = visitor.getNamespace();
        Assert.assertEquals("examples.subpackage", namespace.getName());
        Assert.assertEquals("examples", namespace.getParent().getName());
        Assert.assertEquals(Namespace.TOP, namespace.getParent().getParent());
    }

    @Override
    protected Class<NamespaceVisitor> getVisitorClass() {
        return NamespaceVisitor.class;
    }
}
