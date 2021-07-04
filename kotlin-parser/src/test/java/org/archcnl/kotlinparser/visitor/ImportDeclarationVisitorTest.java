package org.archcnl.kotlinparser.visitor;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImportDeclarationVisitorTest {

  @Test
  void givenKotlinClass_whenImportDeclarationVisitorVisit_thenImportsFound() throws IOException {
    // given
    final var namedFileContext = TestHelper.getKotlinFileContextFromFile(TestHelper.COMPLEX_CLASS);
    final var importDeclarationVisitor =
        new ImportDeclarationVisitor(namedFileContext.getRulesNames());
    // when
    importDeclarationVisitor.visit(namedFileContext.getFileContext());
    // then
    final var imports = importDeclarationVisitor.getImports();
    Assertions.assertEquals(3, imports.size());
    Assertions.assertEquals("example.subpackage.ClassInSubpackage", imports.get(0).getName());
    Assertions.assertEquals("ClassInSubpackage", imports.get(0).getSimpleName());
    Assertions.assertEquals("example.anotherPackage.AnotherClass", imports.get(1).getName());
    Assertions.assertEquals("AnotherClass", imports.get(1).getSimpleName());
    Assertions.assertEquals("java.util", imports.get(2).getName());
    Assertions.assertEquals("util", imports.get(2).getSimpleName());
  }
}
