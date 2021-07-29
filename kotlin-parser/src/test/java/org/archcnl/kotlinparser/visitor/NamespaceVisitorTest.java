package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamespaceVisitorTest {
    @Test
    public void testNamespaceFromComplexClass() throws IOException {
        final var namedFileContext =
                TestHelper.getKotlinFileContextFromFile(TestHelper.COMPLEX_CLASS);

        final var namespaceVisitor = new NamespaceVisitor(namedFileContext.getRulesNames());
        namespaceVisitor.visit(namedFileContext.getFileContext());

        final var namespace = namespaceVisitor.getNamespace();

        Assertions.assertEquals("example", namespace.getName());
        Assertions.assertEquals(Namespace.TOP, namespace.getParent());
    }

    @Test
    public void testNamespaceFromClassInSubpackage() throws IOException {
        final var namedFileContext =
                TestHelper.getKotlinFileContextFromFile(
                        TestHelper.SUBPACKAGE, TestHelper.CLASS_IN_SUBPACKAGE);

        final var namespaceVisitor = new NamespaceVisitor(namedFileContext.getRulesNames());
        namespaceVisitor.visit(namedFileContext.getFileContext());

        final var namespace = namespaceVisitor.getNamespace();

        Assertions.assertEquals("example.subpackage", namespace.getName());
        Assertions.assertEquals("example", namespace.getParent().getName());
        Assertions.assertEquals(Namespace.TOP, namespace.getParent().getParent());
    }
}
