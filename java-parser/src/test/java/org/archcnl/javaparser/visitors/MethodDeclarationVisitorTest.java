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
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Method;
import org.junit.Before;
import org.junit.Test;

public class MethodDeclarationVisitorTest {

    private MethodDeclarationVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new MethodDeclarationVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EmptyClass.java");
        unit.accept(visitor, null);

        assertTrue(visitor.getMethods().isEmpty());
    }

    @Test
    public void testEnumeration() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "Enumeration.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getMethods().size());

        Method method = visitor.getMethods().get(0);

        assertEquals("getIndex(Enumeration)", method.getSignature());
        assertEquals("getIndex", method.getName());
        assertEquals("int", method.getReturnType().getName());
        assertTrue(method.getReturnType().isPrimitive());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getDeclaredExceptions().size());
        assertEquals(0, method.getThrownExceptions().size());
        assertEquals(0, method.getLocalVariables().size());
        assertEquals(1, method.getParameters().size());
        assertEquals("e", method.getParameters().get(0).getName());
        assertEquals("examples.Enumeration", method.getParameters().get(0).getType().getName());
        assertEquals("Enumeration", method.getParameters().get(0).getType().getSimpleName());
        assertEquals(0, method.getParameters().get(0).getModifiers().size());
        assertEquals(0, method.getParameters().get(0).getAnnotations().size());
        assertEquals(2, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
        assertEquals("static", method.getModifiers().get(1).getName());
    }

    @Test
    public void testSimpleClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "SimpleClass.java");
        unit.accept(visitor, null);

        assertEquals(1, visitor.getMethods().size());

        Method method = visitor.getMethods().get(0);

        assertEquals("get()", method.getSignature());
        assertEquals("get", method.getName());
        assertEquals("int", method.getReturnType().getName());
        assertTrue(method.getReturnType().isPrimitive());
        assertEquals(0, method.getAnnotations().size());
        assertEquals(0, method.getDeclaredExceptions().size());
        assertEquals(0, method.getThrownExceptions().size());
        assertEquals(0, method.getLocalVariables().size());
        assertEquals(0, method.getParameters().size());
        assertEquals(1, method.getModifiers().size());
        assertEquals("public", method.getModifiers().get(0).getName());
    }

    @Test
    public void testComplexClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(4, visitor.getMethods().size());

        Method method1 = visitor.getMethods().get(0);
        Method method2 = visitor.getMethods().get(1);
        Method method3 = visitor.getMethods().get(2);
        Method method4 = visitor.getMethods().get(3);

        assertEquals("calculateArea()", method1.getSignature());
        assertEquals("stringMethod()", method2.getSignature());
        assertEquals("referenceMethod(ClassInSubpackage)", method3.getSignature());
        assertEquals("primitiveMethod(boolean)", method4.getSignature());

        assertEquals(2, method2.getAnnotations().size());
        assertEquals("Override", method2.getAnnotations().get(0).getName());
        assertEquals(0, method2.getAnnotations().get(0).getValues().size());
        assertEquals("SuppressWarnings", method2.getAnnotations().get(1).getName());
        assertEquals(0, method2.getAnnotations().get(1).getValues().size());
        assertEquals(0, method2.getLocalVariables().size());

        assertEquals(1, method3.getParameters().size());
        assertEquals("parameter", method3.getParameters().get(0).getName());

        assertEquals(
                "examples.subpackage.ClassInSubpackage",
                method3.getParameters().get(0).getType().getName());
        assertEquals("examples.SimpleClass", method3.getReturnType().getName());

        assertEquals(1, method4.getLocalVariables().size());
        assertEquals("characters", method4.getLocalVariables().get(0).getName());
        assertEquals("java.util.List", method4.getLocalVariables().get(0).getType().getName());
        assertFalse(method4.getLocalVariables().get(0).getType().isPrimitive());
    }
}
