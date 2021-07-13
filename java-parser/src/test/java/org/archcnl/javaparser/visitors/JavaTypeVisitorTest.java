package org.archcnl.javaparser.visitors;

import java.io.FileNotFoundException;
import java.util.List;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.AnnotationAttribute;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Enumeration;
import org.junit.Assert;
import org.junit.Test;
import com.github.javaparser.ast.CompilationUnit;

public class JavaTypeVisitorTest extends GenericVisitorTest<JavaTypeVisitor> {

  @Test
  public void givenValidJavaClass_whenJavaTypeVisitorVisit_thenTypeFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.SIMPLE_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());

    final DefinedType definedType = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.SimpleClass", definedType.getName());
    Assert.assertEquals("SimpleClass", definedType.getSimpleName());
    Assert.assertTrue(definedType instanceof ClassOrInterface);

    final ClassOrInterface definedClass = (ClassOrInterface) definedType;
    Assert.assertEquals(2, definedClass.getModifiers().size());
    Assert.assertEquals("public", definedClass.getModifiers().get(0).getName());
    Assert.assertEquals("final", definedClass.getModifiers().get(1).getName());
    Assert.assertFalse(definedClass.isInterface());
  }

  @Test
  public void givenValidInterface_whenJavaTypeVisitorVisit_thenTypeFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.INTERFACE);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());

    final DefinedType definedType = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.Interface", definedType.getName());
    Assert.assertEquals("Interface", definedType.getSimpleName());
    Assert.assertTrue(definedType instanceof ClassOrInterface);

    final ClassOrInterface definedClass = (ClassOrInterface) definedType;
    Assert.assertEquals(1, definedClass.getModifiers().size());
    Assert.assertEquals("public", definedClass.getModifiers().get(0).getName());
    Assert.assertTrue(definedClass.isInterface());
  }

  @Test
  public void givenValidAnnotation_whenJavaTypeVisitorVisit_thenTypeFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.ANNOTATION);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());

    final DefinedType definedType = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.Annotation", definedType.getName());
    Assert.assertEquals("Annotation", definedType.getSimpleName());
    Assert.assertEquals(1, definedType.getModifiers().size());
    Assert.assertEquals("public", definedType.getModifiers().get(0).getName());
    Assert.assertTrue(definedType instanceof Annotation);

    final Annotation definedAnnotation = (Annotation) definedType;
    final List<AnnotationAttribute> attributes = definedAnnotation.getAttributes();
    Assert.assertEquals(3, definedAnnotation.getAttributes().size());
    Assert.assertEquals("string", attributes.get(0).getName());
    Assert.assertEquals("java.lang.String", attributes.get(0).getType().getName());
    Assert.assertFalse(attributes.get(0).getType().isPrimitive());
    Assert.assertEquals("integer", attributes.get(1).getName());
    Assert.assertEquals("int", attributes.get(1).getType().getName());
    Assert.assertTrue(attributes.get(1).getType().isPrimitive());
    Assert.assertEquals("floatingPoint", attributes.get(2).getName());
    Assert.assertEquals("float", attributes.get(2).getType().getName());
    Assert.assertTrue(attributes.get(2).getType().isPrimitive());
  }

  @Test
  public void givenValidEnum_whenJavaTypeVisitorVisit_thenTypeFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.ENUM);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());

    final DefinedType definedType = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.Enumeration", definedType.getName());
    Assert.assertEquals("Enumeration", definedType.getSimpleName());
    Assert.assertTrue(definedType instanceof Enumeration);
  }

  @Test
  public void givenValidClass_whenJavaTypeVisitorVisit_thenTypeForOuterClassFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.INNER_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());

    final DefinedType definedType = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.ClassWithInnerClass", definedType.getName());
    Assert.assertTrue(definedType instanceof ClassOrInterface);

    final ClassOrInterface definedClass = (ClassOrInterface) definedType;
    Assert.assertFalse(definedClass.isInterface());
    Assert.assertEquals(1, definedClass.getModifiers().size());
    Assert.assertEquals("public", definedClass.getModifiers().get(0).getName());
    Assert.assertEquals(2, definedClass.getFields().size());
    Assert.assertEquals("field1", definedClass.getFields().get(0).getName());
    Assert.assertEquals("field2", definedClass.getFields().get(1).getName());
    Assert.assertEquals(1, definedClass.getNestedTypes().size());
    Assert.assertEquals("InnerClass", definedClass.getNestedTypes().get(0).getSimpleName());
    Assert.assertEquals("examples.ClassWithInnerClass.InnerClass",
        definedClass.getNestedTypes().get(0).getName());
    Assert.assertEquals(2, definedClass.getMethods().size());
    Assert.assertFalse(definedClass.getMethods().get(0).isConstructor());
    Assert.assertEquals("method()", definedClass.getMethods().get(0).getSignature());
    Assert.assertTrue(definedClass.getMethods().get(1).isConstructor());
    Assert.assertEquals("ClassWithInnerClass(int, float)",
        definedClass.getMethods().get(1).getSignature());
    Assert.assertEquals(0, definedClass.getAnnotations().size());
    Assert.assertEquals(1, definedClass.getModifiers().size());
    Assert.assertEquals("public", definedClass.getModifiers().get(0).getName());
  }

  @Test
  public void givenValidClass_whenJavaTypeVisitorVisit_thenTypeForInnerClassFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.INNER_CLASS);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(1, visitor.getDefinedTypes().size());
    Assert.assertTrue(visitor.getDefinedTypes().get(0) instanceof ClassOrInterface);

    final ClassOrInterface outerType = (ClassOrInterface) visitor.getDefinedTypes().get(0);
    Assert.assertEquals(1, outerType.getNestedTypes().size());

    final DefinedType innerType = outerType.getNestedTypes().get(0);
    Assert.assertEquals("examples.ClassWithInnerClass.InnerClass", innerType.getName());
    Assert.assertEquals("InnerClass", innerType.getSimpleName());
    Assert.assertTrue(innerType instanceof ClassOrInterface);

    final ClassOrInterface innerClass = (ClassOrInterface) innerType;
    Assert.assertEquals(2, innerClass.getModifiers().size());
    Assert.assertEquals("private", innerClass.getModifiers().get(0).getName());
    Assert.assertEquals("static", innerClass.getModifiers().get(1).getName());
    Assert.assertFalse(innerClass.isInterface());
    Assert.assertEquals(1, innerClass.getFields().size());
    Assert.assertEquals("innerField", innerClass.getFields().get(0).getName());
  }

  @Test
  public void givenTwoValidClasses_whenJavaTypeVisitorVisit_thenTypesClassesFound()
      throws FileNotFoundException, FileIsNotAJavaClassException {
    // given
    final CompilationUnit unit = CompilationUnitFactory.getFromPath(
        GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.TWO_CLASSES);
    // when
    unit.accept(visitor, null);
    // then
    Assert.assertEquals(2, visitor.getDefinedTypes().size());

    final DefinedType type1 = visitor.getDefinedTypes().get(0);
    Assert.assertEquals("examples.TwoTopLevelClasses", type1.getName());
    Assert.assertTrue(type1 instanceof ClassOrInterface);

    final DefinedType type2 = visitor.getDefinedTypes().get(1);
    Assert.assertEquals("examples.OtherTopLevelClass", type2.getName());
    Assert.assertTrue(type2 instanceof ClassOrInterface);

    final ClassOrInterface class1 = (ClassOrInterface) type1;
    Assert.assertEquals(1, class1.getModifiers().size());
    Assert.assertEquals("public", class1.getModifiers().get(0).getName());

    final ClassOrInterface class2 = (ClassOrInterface) type2;
    Assert.assertEquals(0, class2.getModifiers().size());
  }

  @Override
  protected Class<JavaTypeVisitor> getVisitorClass() {
    return JavaTypeVisitor.class;
  }
}
