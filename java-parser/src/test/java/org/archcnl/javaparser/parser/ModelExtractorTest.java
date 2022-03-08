package org.archcnl.javaparser.parser;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.ClassInterfaceEnum;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelExtractorTest {

    private static final String PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT =
            "./src/test/resources/examples/extractortest/";
    private static final String SUBPACKAGE_NAME_IN_EXAMPLE_PROJECT = "/namespace/";

    private static final String CLASS_A = "ClassA.java";
    private static final String CLASS_B = "ClassB.java";
    private static final String CLASS_C = "ClassC.java";
    private static final String ENUM = "Enumeration.java";
    private static final String ANNOTATION = "Annotation.java";
    private static final String INTERFACE = "Interface.java";

    private Map<String, SourceFile> projectUnits = new HashMap<>();

    @Before
    public void setUp() {
        final ModelExtractor extractor =
                new ModelExtractor(
                        Arrays.asList(
                                Path.of(
                                        ModelExtractorTest
                                                .PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT)));
        final Project model = extractor.extractCodeModel();
        model.getSourceFiles().stream()
                .forEach(f -> projectUnits.put(f.getPath().getFileName().toString(), f));
    }

    @Test
    public void givenValidJavaProject_whenModelExtract_thenAllExpectedFilesFound() {
        // given example java project, when extractor search and extract source files in setUp()
        // then project shouldn't have any unexpected files
        Assert.assertEquals(6, projectUnits.size());
    }

    @Test
    public void givenValidInterfaceInJavaProject_whenModelExtract_thenInterfaceCorrectParsed() {
        // given, when
        final SourceFile interfaceUnderTest = projectUnits.get(ModelExtractorTest.INTERFACE);
        // then
        Assert.assertEquals(0, interfaceUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.INTERFACE),
                interfaceUnderTest.getPath());
        Assert.assertEquals(
                Namespace.TOP, interfaceUnderTest.getNamespace().getParent().getParent());
        Assert.assertEquals("examples.extractortest", interfaceUnderTest.getNamespace().getName());
        Assert.assertEquals("examples", interfaceUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(1, interfaceUnderTest.getDefinedTypes().size());
        Assert.assertTrue(interfaceUnderTest.getDefinedTypes().get(0) instanceof ClassOrInterface);

        final ClassOrInterface type =
                (ClassOrInterface) interfaceUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(0, type.getSupertypes().size());
        Assert.assertEquals(1, type.getMethods().size());

        final Method method = type.getMethods().get(0);
        Assert.assertEquals("someMethod", method.getName());
        Assert.assertEquals(0, method.getAnnotations().size());
        Assert.assertEquals(0, method.getLocalVariables().size());
        Assert.assertFalse(method.isConstructor());
        Assert.assertEquals(1, method.getDeclaredExceptions().size());
        Assert.assertEquals("java.lang.Exception", method.getDeclaredExceptions().get(0).getName());
        Assert.assertTrue(method.getPath().equals(interfaceUnderTest.getPath()));
        Assert.assertEquals(4, method.getBeginning().get().line);
    }

    @Test
    public void givenValidClassInRootPackage_whenModelExtract_thenClassCorrectParsed() {
        // given, when
        final SourceFile classUnderTest = projectUnits.get(ModelExtractorTest.CLASS_A);
        // then
        Assert.assertEquals(3, classUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.CLASS_A),
                classUnderTest.getPath());
        Assert.assertEquals(Namespace.TOP, classUnderTest.getNamespace().getParent().getParent());
        Assert.assertEquals("examples.extractortest", classUnderTest.getNamespace().getName());
        Assert.assertEquals("examples", classUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(1, classUnderTest.getDefinedTypes().size());
        Assert.assertTrue(classUnderTest.getDefinedTypes().get(0) instanceof ClassOrInterface);

        final ClassOrInterface type = (ClassOrInterface) classUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(2, type.getFields().size());
        Assert.assertEquals(1, type.getSupertypes().size());
    }

    @Test
    public void givenValidClassInSubpackage_whenModelExtract_thenClassCorrectParsed() {
        // given, when
        final SourceFile classUnderTest = projectUnits.get(ModelExtractorTest.CLASS_B);
        // then
        Assert.assertEquals(0, classUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.SUBPACKAGE_NAME_IN_EXAMPLE_PROJECT,
                        ModelExtractorTest.CLASS_B),
                classUnderTest.getPath());
        Assert.assertEquals(
                Namespace.TOP, classUnderTest.getNamespace().getParent().getParent().getParent());
        Assert.assertEquals(
                "examples.extractortest.namespace", classUnderTest.getNamespace().getName());
        Assert.assertEquals(
                "examples.extractortest", classUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(
                "examples", classUnderTest.getNamespace().getParent().getParent().getName());

        Assert.assertEquals(1, classUnderTest.getDefinedTypes().size());
        Assert.assertTrue(classUnderTest.getDefinedTypes().get(0) instanceof ClassOrInterface);

        final ClassOrInterface type = (ClassOrInterface) classUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(1, type.getFields().size());
        Assert.assertEquals(0, type.getSupertypes().size());
        Assert.assertEquals(2, type.getMethods().size());

        final Method method1 = type.getMethods().get(0);
        Assert.assertEquals("ClassB", method1.getName());
        Assert.assertEquals(0, method1.getAnnotations().size());
        Assert.assertEquals(0, method1.getLocalVariables().size());
        Assert.assertTrue(method1.isConstructor());
        Assert.assertEquals(0, method1.getDeclaredExceptions().size());
        Assert.assertEquals(0, method1.getThrownExceptions().size());
        Assert.assertEquals(1, method1.getParameters().size());
        Assert.assertEquals("b", method1.getParameters().get(0).getName());
        Assert.assertEquals("int", method1.getParameters().get(0).getType().getName());
        Assert.assertTrue(
                method1.getParameters()
                        .get(0)
                        .getPath()
                        .equals(classUnderTest.getPath()));
        Assert.assertEquals(7, method1.getParameters().get(0).getBeginning().get().line);
        Assert.assertEquals(1, method1.getModifiers().size());
        Assert.assertEquals("public", method1.getModifiers().get(0).getName());
        Assert.assertTrue(method1.getPath().equals(classUnderTest.getPath()));
        Assert.assertEquals(7, method1.getBeginning().get().line);

        final Method method2 = type.getMethods().get(1);
        Assert.assertEquals("ClassB", method2.getName());
        Assert.assertEquals(1, method2.getAnnotations().size());
        Assert.assertEquals("Deprecated", method2.getAnnotations().get(0).getName());
        Assert.assertEquals(0, method2.getAnnotations().get(0).getValues().size());
        Assert.assertEquals(1, method2.getLocalVariables().size());
        Assert.assertEquals("int", method2.getLocalVariables().get(0).getType().getName());
        Assert.assertEquals("local", method2.getLocalVariables().get(0).getName());
        Assert.assertTrue(method2.isConstructor());
        Assert.assertEquals(0, method2.getDeclaredExceptions().size());
        Assert.assertEquals(0, method2.getThrownExceptions().size());
        Assert.assertEquals(1, method2.getParameters().size());
        Assert.assertEquals("b", method2.getParameters().get(0).getName());
        Assert.assertEquals("Integer", method2.getParameters().get(0).getType().getSimpleName());
        Assert.assertTrue(
                method2.getParameters()
                        .get(0)
                        .getPath()
                        .equals(classUnderTest.getPath()));
        Assert.assertEquals(12, method2.getParameters().get(0).getBeginning().get().line);
        Assert.assertEquals(1, method2.getModifiers().size());
        Assert.assertEquals("public", method2.getModifiers().get(0).getName());
        Assert.assertTrue(method2.getPath().equals(classUnderTest.getPath()));
        Assert.assertEquals(11, method2.getBeginning().get().line);
    }

    @Test
    public void
            givenValidClassInSubpackageWithStaticMethod_whenModelExtract_thenClassCorrectParsed() {
        // given, when
        final SourceFile classUnderTest = projectUnits.get(ModelExtractorTest.CLASS_C);
        // then
        Assert.assertEquals(2, classUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.SUBPACKAGE_NAME_IN_EXAMPLE_PROJECT,
                        ModelExtractorTest.CLASS_C),
                classUnderTest.getPath());
        Assert.assertEquals(
                Namespace.TOP, classUnderTest.getNamespace().getParent().getParent().getParent());
        Assert.assertEquals(
                "examples.extractortest.namespace", classUnderTest.getNamespace().getName());
        Assert.assertEquals(
                "examples.extractortest", classUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(
                "examples", classUnderTest.getNamespace().getParent().getParent().getName());

        Assert.assertEquals(1, classUnderTest.getDefinedTypes().size());
        Assert.assertTrue(classUnderTest.getDefinedTypes().get(0) instanceof ClassOrInterface);

        final ClassOrInterface type = (ClassOrInterface) classUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(0, type.getFields().size());
        Assert.assertEquals(0, type.getSupertypes().size());
        Assert.assertEquals(1, type.getMethods().size());

        final Method method = type.getMethods().get(0);
        Assert.assertEquals("staticMethod", method.getName());
        Assert.assertEquals(0, method.getAnnotations().size());
        Assert.assertEquals(1, method.getLocalVariables().size());
        Assert.assertEquals("localVariable", method.getLocalVariables().get(0).getName());
        Assert.assertFalse(method.isConstructor());
        Assert.assertEquals(1, method.getDeclaredExceptions().size());
        Assert.assertEquals("java.lang.Exception", method.getDeclaredExceptions().get(0).getName());
        Assert.assertEquals(1, method.getCaughtExceptions().size());
        Assert.assertEquals(
                "java.lang.NullPointerException", method.getCaughtExceptions().get(0).getName());
        Assert.assertEquals(1, method.getParameters().size());
        Assert.assertEquals("items", method.getParameters().get(0).getName());
        Assert.assertTrue(
                method.getParameters()
                        .get(0)
                        .getPath()
                        .equals(classUnderTest.getPath()));
        Assert.assertEquals(7, method.getParameters().get(0).getBeginning().get().line);
        Assert.assertEquals(2, method.getModifiers().size());
        Assert.assertEquals("public", method.getModifiers().get(0).getName());
        Assert.assertEquals("static", method.getModifiers().get(1).getName());
        Assert.assertTrue(method.getPath().equals(classUnderTest.getPath()));
        Assert.assertEquals(7, method.getBeginning().get().line);
    }

    @Test
    public void givenValidAnnotationInJavaProject_whenModelExtract_thenAnnotationCorrectParsed() {
        // given, when
        final SourceFile annotationUnderTest = projectUnits.get(ModelExtractorTest.ANNOTATION);
        // then
        Assert.assertEquals(0, annotationUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.SUBPACKAGE_NAME_IN_EXAMPLE_PROJECT,
                        ModelExtractorTest.ANNOTATION),
                annotationUnderTest.getPath());
        Assert.assertEquals(
                Namespace.TOP,
                annotationUnderTest.getNamespace().getParent().getParent().getParent());
        Assert.assertEquals(
                "examples.extractortest.namespace", annotationUnderTest.getNamespace().getName());
        Assert.assertEquals(
                "examples.extractortest", annotationUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(
                "examples", annotationUnderTest.getNamespace().getParent().getParent().getName());
        Assert.assertEquals(1, annotationUnderTest.getDefinedTypes().size());
        Assert.assertTrue(annotationUnderTest.getDefinedTypes().get(0) instanceof Annotation);

        final Annotation type = (Annotation) annotationUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(1, type.getAttributes().size());
        Assert.assertEquals("description", type.getAttributes().get(0).getName());
        Assert.assertEquals("String", type.getAttributes().get(0).getType().getSimpleName());
    }

    @Test
    public void givenValidEnumInJavaProject_whenModelExtract_thenEnumCorrectParsed() {
        // given, when
        final SourceFile enumUnderTest = projectUnits.get(ModelExtractorTest.ENUM);
        // then
        Assert.assertEquals(0, enumUnderTest.getImportedTypes().size());
        Assert.assertEquals(
                Path.of(
                        ModelExtractorTest.PATH_TO_PACKAGE_WITH_EXAMPLE_JAVA_PROJECT,
                        ModelExtractorTest.ENUM),
                enumUnderTest.getPath());
        Assert.assertEquals(Namespace.TOP, enumUnderTest.getNamespace().getParent().getParent());
        Assert.assertEquals("examples.extractortest", enumUnderTest.getNamespace().getName());
        Assert.assertEquals("examples", enumUnderTest.getNamespace().getParent().getName());
        Assert.assertEquals(1, enumUnderTest.getDefinedTypes().size());
        Assert.assertTrue(enumUnderTest.getDefinedTypes().get(0) instanceof ClassInterfaceEnum);

        final ClassInterfaceEnum type = (ClassInterfaceEnum) enumUnderTest.getDefinedTypes().get(0);
        Assert.assertEquals(0, type.getNestedTypes().size());
        Assert.assertEquals(1, type.getMethods().size());
        Assert.assertEquals(1, type.getFields().size());
        Assert.assertEquals(0, type.getFields().get(0).getAnnotations().size());
        Assert.assertEquals(1, type.getFields().get(0).getModifiers().size());
        Assert.assertEquals("private", type.getFields().get(0).getModifiers().get(0).getName());
        Assert.assertEquals("String", type.getFields().get(0).getType().getSimpleName());
        Assert.assertEquals("field", type.getFields().get(0).getName());

        final Method method = type.getMethods().get(0);
        Assert.assertEquals("isOtherValue", method.getName());
        Assert.assertEquals(0, method.getAnnotations().size());
        Assert.assertEquals(0, method.getLocalVariables().size());
        Assert.assertFalse(method.isConstructor());
        Assert.assertEquals(0, method.getDeclaredExceptions().size());
        Assert.assertEquals(0, method.getThrownExceptions().size());
        Assert.assertEquals(0, method.getParameters().size());
        Assert.assertEquals(1, method.getModifiers().size());
        Assert.assertEquals("public", method.getModifiers().get(0).getName());
        Assert.assertEquals("boolean", method.getReturnType().getName());
        Assert.assertTrue(method.getPath().equals(enumUnderTest.getPath()));
        Assert.assertEquals(9, method.getBeginning().get().line);
    }
}
