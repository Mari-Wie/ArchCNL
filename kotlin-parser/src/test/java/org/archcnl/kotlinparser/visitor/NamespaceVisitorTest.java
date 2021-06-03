package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.junit.jupiter.api.Test;

public class NamespaceVisitorTest {
    @Test
    public void testNamespaceFromComplexClass() throws IOException {
        var namedFileContext = TestHelper.getKotlinFileContextFromFile("ComplexClass.kt");

        var namespaceVisitor = new NamespaceVisitor(namedFileContext.getRulesNames());
        namespaceVisitor.visit(namedFileContext.getFileContext());

        var namespace = namespaceVisitor.getNamespace();

        assertEquals("example", namespace.getName());
        assertEquals(Namespace.TOP, namespace.getParent());
    }

    @Test
    public void testNamespaceFromClassInSubpackage() throws IOException {
        var namedFileContext =
                TestHelper.getKotlinFileContextFromFile("subpackage", "ClassInSubpackage.kt");

        var namespaceVisitor = new NamespaceVisitor(namedFileContext.getRulesNames());
        namespaceVisitor.visit(namedFileContext.getFileContext());

        var namespace = namespaceVisitor.getNamespace();

        assertEquals("example.subpackage", namespace.getName());
        assertEquals("example", namespace.getParent().getName());
        assertEquals(Namespace.TOP, namespace.getParent().getParent());
    }
}
