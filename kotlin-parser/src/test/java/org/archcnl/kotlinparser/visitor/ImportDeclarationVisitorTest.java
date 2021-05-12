package org.archcnl.kotlinparser.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.archcnl.kotlinparser.parser.KtParser;
import org.junit.jupiter.api.Test;

class ImportDeclarationVisitorTest {

    @Test
    void testImportListFromComplexClass() throws IOException {
        var parser = new KtParser();
        var contentOfComplexClass = TestHelper.getContentOfExampleFile("ComplexClass.kt");
        var treeOfComplexClass = parser.parse(contentOfComplexClass);

        var importDeclarationVisitor = new ImportDeclarationVisitor();
        importDeclarationVisitor.visit(treeOfComplexClass);

        var imports = importDeclarationVisitor.getImports();

        assertEquals(2, imports.size());
        assertEquals("example.subpackage.ClassInSubpackage", imports.get(0).getName());
        assertEquals("ClassInSubpackage", imports.get(0).getSimpleName());
        assertEquals("example.anotherPackage.AnotherClass", imports.get(1).getName());
        assertEquals("AnotherClass", imports.get(1).getSimpleName());
    }
}
