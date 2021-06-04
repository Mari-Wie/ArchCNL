package org.archcnl.javaparser.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.util.Arrays;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.ClassInterfaceEnum;
import org.archcnl.owlify.famix.codemodel.Method;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.junit.Before;
import org.junit.Test;

public class ModelExtractorTest {

    private final Path pathToPackage = Path.of("./src/test/java/examples/extractortest/");

    private SourceFile classA;
    private SourceFile classB;
    private SourceFile classC;
    private SourceFile enumeration;
    private SourceFile annotation;
    private SourceFile interfacE;

    @Before
    public void setUp() {
        ModelExtractor extractor = new ModelExtractor(Arrays.asList(pathToPackage));

        Project model = extractor.extractCodeModel();

        assertEquals(6, model.getSourceFiles().size());

        // assign the different source files, fail when an unexpected file is contained
        for (SourceFile f : model.getSourceFiles()) {
            switch (f.getPath().getFileName().toString()) {
                case "ClassA.java":
                    classA = f;
                    break;
                case "ClassB.java":
                    classB = f;
                    break;
                case "ClassC.java":
                    classC = f;
                    break;
                case "Annotation.java":
                    annotation = f;
                    break;
                case "Enumeration.java":
                    enumeration = f;
                    break;
                case "Interface.java":
                    interfacE = f;
                    break;
                default:
                    fail("Unexpected source file in code model: " + f.getPath());
            }
        }
    }

    @Test
    public void testInterface() {
        assertEquals(0, interfacE.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/Interface.java"), interfacE.getPath());
        assertEquals(Namespace.TOP, interfacE.getNamespace().getParent().getParent());
        assertEquals("examples.extractortest", interfacE.getNamespace().getName());
        assertEquals("examples", interfacE.getNamespace().getParent().getName());

        assertEquals(1, interfacE.getDefinedTypes().size());
        assertTrue(interfacE.getDefinedTypes().get(0) instanceof ClassOrInterface);

        ClassOrInterface type = (ClassOrInterface) interfacE.getDefinedTypes().get(0);

        assertEquals(0, type.getSupertypes().size());
        assertEquals(1, type.getMethods().size());

        Method method = type.getMethods().get(0);

        assertEquals("someMethod", method.getName());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getLocalVariables().size());
        assertFalse(method.isConstructor());
        assertEquals(1, method.getDeclaredExceptions().size());
        assertEquals("java.lang.Exception", method.getDeclaredExceptions().get(0).getName());
    }

    @Test
    public void testClassA() {
        assertEquals(3, classA.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/ClassA.java"), classA.getPath());
        assertEquals(Namespace.TOP, classA.getNamespace().getParent().getParent());
        assertEquals("examples.extractortest", classA.getNamespace().getName());
        assertEquals("examples", classA.getNamespace().getParent().getName());

        assertEquals(1, classA.getDefinedTypes().size());
        assertTrue(classA.getDefinedTypes().get(0) instanceof ClassOrInterface);

        ClassOrInterface type = (ClassOrInterface) classA.getDefinedTypes().get(0);

        assertEquals(2, type.getFields().size());
        assertEquals(1, type.getSupertypes().size());
    }

    @Test
    public void testClassB() {
        assertEquals(0, classB.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/namespace/ClassB.java"), classB.getPath());
        assertEquals(Namespace.TOP, classB.getNamespace().getParent().getParent().getParent());
        assertEquals("examples.extractortest.namespace", classB.getNamespace().getName());
        assertEquals("examples.extractortest", classB.getNamespace().getParent().getName());
        assertEquals("examples", classB.getNamespace().getParent().getParent().getName());

        assertEquals(1, classB.getDefinedTypes().size());
        assertTrue(classB.getDefinedTypes().get(0) instanceof ClassOrInterface);

        ClassOrInterface type = (ClassOrInterface) classB.getDefinedTypes().get(0);

        assertEquals(1, type.getFields().size());
        assertEquals(0, type.getSupertypes().size());
        assertEquals(2, type.getMethods().size());

        Method method1 = type.getMethods().get(0);

        assertEquals("ClassB", method1.getName());
        assertEquals(0, method1.getAnnotations().size());
        assertEquals(0, method1.getLocalVariables().size());
        assertTrue(method1.isConstructor());    
        assertEquals(0, method1.getDeclaredExceptions().size());
        assertEquals(0, method1.getThrownExceptions().size());
        assertEquals(1, method1.getParameters().size());
        assertEquals("b", method1.getParameters().get(0).getName());
        assertEquals("int", method1.getParameters().get(0).getType().getName());
        assertEquals(1, method1.getModifiers().size());
        assertEquals("public", method1.getModifiers().get(0).getName());

        Method method2 = type.getMethods().get(1);
        
        assertEquals("ClassB", method2.getName());
        assertEquals(1, method2.getAnnotations().size());
        assertEquals("Deprecated", method2.getAnnotations().get(0).getName());
        assertEquals(0, method2.getAnnotations().get(0).getValues().size());
        assertEquals(1, method2.getLocalVariables().size());
        assertEquals("int", method2.getLocalVariables().get(0).getType().getName());
        assertEquals("local", method2.getLocalVariables().get(0).getName());
        assertTrue(method2.isConstructor());
        assertEquals(0, method2.getDeclaredExceptions().size());
        assertEquals(0, method2.getThrownExceptions().size());
        assertEquals(1, method2.getParameters().size());
        assertEquals("b", method2.getParameters().get(0).getName());
        assertEquals("Integer", method2.getParameters().get(0).getType().getSimpleName());
        assertEquals(1, method2.getModifiers().size());
        assertEquals("public", method2.getModifiers().get(0).getName());
    }

    @Test
    public void testClassC() {
        assertEquals(2, classC.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/namespace/ClassC.java"), classC.getPath());
        assertEquals(Namespace.TOP, classC.getNamespace().getParent().getParent().getParent());
        assertEquals("examples.extractortest.namespace", classC.getNamespace().getName());
        assertEquals("examples.extractortest", classC.getNamespace().getParent().getName());
        assertEquals("examples", classC.getNamespace().getParent().getParent().getName());

        assertEquals(1, classC.getDefinedTypes().size());
        assertTrue(classC.getDefinedTypes().get(0) instanceof ClassOrInterface);

        ClassOrInterface type = (ClassOrInterface) classC.getDefinedTypes().get(0);

        assertEquals(0, type.getFields().size());
        assertEquals(0, type.getSupertypes().size());
        assertEquals(1, type.getMethods().size());

        Method method = type.getMethods().get(0);

        assertEquals("staticMethod", method.getName());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(1, method.getLocalVariables().size());
        assertEquals("localVariable", method.getLocalVariables().get(0).getName());
        assertFalse(method.isConstructor());
        assertEquals(1, method.getDeclaredExceptions().size());
        assertEquals("java.lang.Exception", method.getDeclaredExceptions().get(0).getName());
        assertEquals(1, method.getCaughtExceptions().size());
        assertEquals("java.lang.NullPointerException", method.getCaughtExceptions().get(0).getName());
        assertEquals(1, method.getParameters().size());
        assertEquals("items", method.getParameters().get(0).getName());
        assertEquals(2, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
        assertEquals("static", method.getModifiers().get(1).getName());




    }

    @Test
    public void testAnnotation() {
        assertEquals(0, annotation.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/namespace/Annotation.java"), annotation.getPath());
        assertEquals(Namespace.TOP, annotation.getNamespace().getParent().getParent().getParent());
        assertEquals("examples.extractortest.namespace", annotation.getNamespace().getName());
        assertEquals("examples.extractortest", annotation.getNamespace().getParent().getName());
        assertEquals("examples", annotation.getNamespace().getParent().getParent().getName());

        assertEquals(1, annotation.getDefinedTypes().size());
        assertTrue(annotation.getDefinedTypes().get(0) instanceof Annotation);

        Annotation type = (Annotation) annotation.getDefinedTypes().get(0);

        assertEquals(1, type.getAttributes().size());
        assertEquals("description", type.getAttributes().get(0).getName());
        assertEquals("String", type.getAttributes().get(0).getType().getSimpleName());
    }

    @Test
    public void testEnumeration() {
        assertEquals(0, enumeration.getImportedTypes().size());
        assertEquals(Path.of(pathToPackage + "/Enumeration.java"), enumeration.getPath());
        assertEquals(Namespace.TOP, enumeration.getNamespace().getParent().getParent());
        assertEquals("examples.extractortest", enumeration.getNamespace().getName());
        assertEquals("examples", enumeration.getNamespace().getParent().getName());

        assertEquals(1, enumeration.getDefinedTypes().size());
        assertTrue(enumeration.getDefinedTypes().get(0) instanceof ClassInterfaceEnum);

        ClassInterfaceEnum type = (ClassInterfaceEnum) enumeration.getDefinedTypes().get(0);

        assertEquals(0, type.getNestedTypes().size());
        assertEquals(1, type.getMethods().size());
        assertEquals(1, type.getFields().size());
        assertEquals(0, type.getFields().get(0).getAnnotations().size());
        assertEquals(1, type.getFields().get(0).getModifiers().size());
        assertEquals("private", type.getFields().get(0).getModifiers().get(0).getName());     
        assertEquals("String", type.getFields().get(0).getType().getSimpleName());
        assertEquals("field", type.getFields().get(0).getName());   


        Method method = type.getMethods().get(0);

        assertEquals("isOtherValue", method.getName());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getLocalVariables().size());
        assertFalse(method.isConstructor());
        assertEquals(0, method.getDeclaredExceptions().size());
        assertEquals(0, method.getThrownExceptions().size());
        assertEquals(0, method.getParameters().size());
        assertEquals(1, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
        assertEquals("boolean", method.getReturnType().getName());

    }
        


}
