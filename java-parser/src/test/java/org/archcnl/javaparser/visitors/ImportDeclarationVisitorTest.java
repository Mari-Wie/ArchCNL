package org.archcnl.javaparser.visitors;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Assert;
import org.junit.Test;
import com.github.javaparser.ast.CompilationUnit;

public class ImportDeclarationVisitorTest extends GenericVisitorTest<ImportDeclarationVisitor> {

  @Test
  public void givenEmtyJavaClass_whenImportDeclarationVisitorVisit_thenNoImportsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given, when
    final List<String> imports = extractImportedTypes(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.EMPTY_CLASS);
    // then
    Assert.assertEquals(0, imports.size());
  }

  @Test
  public void givenValidJavaClass_whenImportDeclarationVisitorVisit_thenAllImportsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given, when
    final List<String> imports = extractImportedTypes(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.COMPLEX_CLASS);
    // then
    Assert.assertEquals(3, imports.size());
    Assert.assertTrue(imports.contains("examples.subpackage.ClassInSubpackage"));
    Assert.assertTrue(imports.contains("java.util.ArrayList"));
    Assert.assertTrue(imports.contains("java.util.List"));
  }

  @Test
  public void givenValidJavaClassFromSubpackage_whenImportDeclarationVisitorVisit_thenAllImportsFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given, when
    final List<String> imports =
        extractImportedTypes(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
            + GenericVisitorTest.TEST_SUBPACKAGE + GenericVisitorTest.CLASS_A);
    // then
    Assert.assertEquals(3, imports.size());
    Assert.assertTrue(imports.contains("examples.extractortest.namespace.ClassB"));
    Assert.assertTrue(imports.contains("examples.extractortest.namespace.ClassC"));
    Assert.assertTrue(imports.contains("java.util.Arrays"));
  }

  @Test
  public void givenValidJavaClass_whenImportDeclarationVisitorVisit_thenImportsAsSimpleNamesFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given, when
    final List<String> imports = extractImportedSimpleNames(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.COMPLEX_CLASS);
    // then
    Assert.assertEquals(3, imports.size());
    Assert.assertTrue(imports.contains("ClassInSubpackage"));
    Assert.assertTrue(imports.contains("ArrayList"));
    Assert.assertTrue(imports.contains("List"));
  }

  @Test
  public void givenValidJavaClass_whenImportDeclarationVisitorVisit_thenStaticImportsAsSimpleNamesFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given, when
    final List<String> imports =
        extractImportedSimpleNames(GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
            + GenericVisitorTest.TEST_SUBPACKAGE + GenericVisitorTest.CLASS_A);
    // then
    Assert.assertEquals(3, imports.size());
    Assert.assertTrue(imports.contains("ClassB"));
    Assert.assertTrue(imports.contains("ClassC"));
    Assert.assertTrue(imports.contains("Arrays"));
  }

  private List<String> extractImportedTypes(final String path)
      throws FileNotFoundException, FileIsNotAJavaClassException {
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(path);
    unit.accept(visitor, null);
    // primitive types cannot be imported
    visitor.getImports().forEach(type -> Assert.assertFalse(type.isPrimitive()));
    return visitor.getImports().stream().map(Type::getName).collect(Collectors.toList());
  }

  private List<String> extractImportedSimpleNames(final String path)
      throws FileNotFoundException, FileIsNotAJavaClassException {
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(path);
    unit.accept(visitor, null);
    // primitive types cannot be imported
    visitor.getImports().forEach(type -> Assert.assertFalse(type.isPrimitive()));
    return visitor.getImports().stream().map(Type::getSimpleName).collect(Collectors.toList());
  }

  @Override
  protected Class<ImportDeclarationVisitor> getVisitorClass() {
    return ImportDeclarationVisitor.class;
  }
}
