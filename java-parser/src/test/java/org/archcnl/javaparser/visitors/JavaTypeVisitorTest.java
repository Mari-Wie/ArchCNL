package org.archcnl.javaparser.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.FileNotFoundException;
import java.util.List;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Annotation;
import org.archcnl.owlify.famix.codemodel.AnnotationAttribute;
import org.archcnl.owlify.famix.codemodel.ClassOrInterface;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.Enumeration;
import org.junit.Before;
import org.junit.Test;

public class JavaTypeVisitorTest {

    private JavaTypeVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new JavaTypeVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testSimpleClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "SimpleClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());

        DefinedType definedType = visitor.getDefinedTypes().get(0);

        assertEquals("examples.SimpleClass", definedType.getName());
        assertEquals("SimpleClass", definedType.getSimpleName());
        assertTrue(definedType instanceof ClassOrInterface);

        ClassOrInterface definedClass = (ClassOrInterface) definedType;

        assertEquals(2, definedClass.getModifiers().size());
        assertEquals("public", definedClass.getModifiers().get(0).getName());
        assertEquals("final", definedClass.getModifiers().get(1).getName());
        assertFalse(definedClass.isInterface());
    }

    @Test
    public void testSimpleInterface() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "Interface.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());

        DefinedType definedType = visitor.getDefinedTypes().get(0);

        assertEquals("examples.Interface", definedType.getName());
        assertEquals("Interface", definedType.getSimpleName());
        assertTrue(definedType instanceof ClassOrInterface);

        ClassOrInterface definedClass = (ClassOrInterface) definedType;

        assertEquals(1, definedClass.getModifiers().size());
        assertEquals("public", definedClass.getModifiers().get(0).getName());
        assertTrue(definedClass.isInterface());
    }

    @Test
    public void testAnnotation() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "Annotation.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());

        DefinedType definedType = visitor.getDefinedTypes().get(0);

        assertEquals("examples.Annotation", definedType.getName());
        assertEquals("Annotation", definedType.getSimpleName());

        assertEquals(1, definedType.getModifiers().size());
        assertEquals("public", definedType.getModifiers().get(0).getName());

        assertTrue(definedType instanceof Annotation);
        Annotation definedAnnotation = (Annotation) definedType;

        List<AnnotationAttribute> attributes = definedAnnotation.getAttributes();

        assertEquals(3, definedAnnotation.getAttributes().size());

        assertEquals("string", attributes.get(0).getName());
        assertEquals("java.lang.String", attributes.get(0).getType().getName());
        assertFalse(attributes.get(0).getType().isPrimitive());

        assertEquals("integer", attributes.get(1).getName());
        assertEquals("int", attributes.get(1).getType().getName());
        assertTrue(attributes.get(1).getType().isPrimitive());

        assertEquals("floatingPoint", attributes.get(2).getName());
        assertEquals("float", attributes.get(2).getType().getName());
        assertTrue(attributes.get(2).getType().isPrimitive());
    }

    @Test
    public void testEnumeration() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "Enumeration.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());

        DefinedType definedType = visitor.getDefinedTypes().get(0);

        assertEquals("examples.Enumeration", definedType.getName());
        assertEquals("Enumeration", definedType.getSimpleName());
        assertTrue(definedType instanceof Enumeration);
    }

    @Test
    public void testNestedClassOuter() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "ClassWithInnerClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());

        DefinedType definedType = visitor.getDefinedTypes().get(0);

        assertEquals("examples.ClassWithInnerClass", definedType.getName());
        assertTrue(definedType instanceof ClassOrInterface);

        ClassOrInterface definedClass = (ClassOrInterface) definedType;

        assertFalse(definedClass.isInterface());
        assertEquals(1, definedClass.getModifiers().size());
        assertEquals("public", definedClass.getModifiers().get(0).getName());

        assertEquals(2, definedClass.getFields().size());
        assertEquals("field1", definedClass.getFields().get(0).getName());
        assertEquals("field2", definedClass.getFields().get(1).getName());

        assertEquals(1, definedClass.getNestedTypes().size());
        assertEquals("InnerClass", definedClass.getNestedTypes().get(0).getSimpleName());
        assertEquals(
                "examples.ClassWithInnerClass.InnerClass",
                definedClass.getNestedTypes().get(0).getName());

        assertEquals(2, definedClass.getMethods().size());
        assertFalse(definedClass.getMethods().get(0).isConstructor());
        assertEquals("method()", definedClass.getMethods().get(0).getSignature());
        assertTrue(definedClass.getMethods().get(1).isConstructor());
        assertEquals(
                "ClassWithInnerClass(int, float)", definedClass.getMethods().get(1).getSignature());

        assertEquals(0, definedClass.getAnnotations().size());

        assertEquals(1, definedClass.getModifiers().size());
        assertEquals("public", definedClass.getModifiers().get(0).getName());
    }

    @Test
    public void testNestedClassInner() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "ClassWithInnerClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getDefinedTypes().size());
        assertTrue(visitor.getDefinedTypes().get(0) instanceof ClassOrInterface);

        ClassOrInterface outerType = (ClassOrInterface) visitor.getDefinedTypes().get(0);

        assertEquals(1, outerType.getNestedTypes().size());

        DefinedType innerType = outerType.getNestedTypes().get(0);

        assertEquals("examples.ClassWithInnerClass.InnerClass", innerType.getName());
        assertEquals("InnerClass", innerType.getSimpleName());
        assertTrue(innerType instanceof ClassOrInterface);

        ClassOrInterface innerClass = (ClassOrInterface) innerType;

        assertEquals(2, innerClass.getModifiers().size());
        assertEquals("private", innerClass.getModifiers().get(0).getName());
        assertEquals("static", innerClass.getModifiers().get(1).getName());
        assertFalse(innerClass.isInterface());
        assertEquals(1, innerClass.getFields().size());
        assertEquals("innerField", innerClass.getFields().get(0).getName());
    }

    @Test
    public void testTwoTopLevelClasses()
            throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "TwoTopLevelClasses.java");
        unit.accept(visitor, null);

        assertEquals(2, visitor.getDefinedTypes().size());

        DefinedType type1 = visitor.getDefinedTypes().get(0);
        DefinedType type2 = visitor.getDefinedTypes().get(1);

        assertEquals("examples.TwoTopLevelClasses", type1.getName());
        assertEquals("examples.OtherTopLevelClass", type2.getName());

        assertTrue(type1 instanceof ClassOrInterface);
        assertTrue(type2 instanceof ClassOrInterface);

        ClassOrInterface class1 = (ClassOrInterface) type1;
        ClassOrInterface class2 = (ClassOrInterface) type2;

        assertEquals(1, class1.getModifiers().size());
        assertEquals("public", class1.getModifiers().get(0).getName());

        assertEquals(0, class2.getModifiers().size());
    }
}
