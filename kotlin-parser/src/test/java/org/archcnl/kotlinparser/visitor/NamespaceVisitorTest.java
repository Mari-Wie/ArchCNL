package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.archcnl.kotlinparser.parser.KtParser;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.junit.jupiter.api.Test;

public class NamespaceVisitorTest {
    @Test
    public void testNamespaceFromComplexClass() throws IOException {
        var parser = new KtParser();
        var contentOfComplexClass = TestHelper.getContentOfExampleFile("ComplexClass.kt");
        var treeOfComplexClass = parser.parse(contentOfComplexClass);

        var namespaceVisitor = new NamespaceVisitor();
        namespaceVisitor.visit(treeOfComplexClass);

        var namespace = namespaceVisitor.getNamespace();

        assertEquals("example", namespace.getName());
        assertEquals(Namespace.TOP, namespace.getParent());
    }

    @Test
    public void testNamespaceFromClassInSubpackage() throws IOException {
        var parser = new KtParser();
        var contentOfClassInSbpackage =
                TestHelper.getContentOfExampleFile("subpackage", "ClassInSubpackage.kt");
        var treeOfClassInSubpackage = parser.parse(contentOfClassInSbpackage);

        var namespaceVisitor = new NamespaceVisitor();
        namespaceVisitor.visit(treeOfClassInSubpackage);

        var namespace = namespaceVisitor.getNamespace();

        assertEquals("example.subpackage", namespace.getName());
        assertEquals("example", namespace.getParent().getName());
        assertEquals(Namespace.TOP, namespace.getParent().getParent());
    }
}
