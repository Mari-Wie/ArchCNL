package org.archcnl.owlify.famix.visitors;

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
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;
import org.archcnl.owlify.famix.parser.CompilationUnitFactory;
import org.junit.Before;
import org.junit.Test;

public class ImportDeclarationVisitorTest {

    private ImportDeclarationVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new ImportDeclarationVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testNoImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EmptyClass.java");
        unit.accept(visitor, null);

        assertTrue(visitor.getImports().isEmpty());
    }

    @Test
    public void testSomeImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(3, visitor.getImports().size());

        assertEquals("java.util.ArrayList", visitor.getImports().get(0).getName());
        assertFalse(visitor.getImports().get(0).isPrimitive());

        assertEquals("java.util.List", visitor.getImports().get(1).getName());
        assertFalse(visitor.getImports().get(1).isPrimitive());

        assertEquals(
                "examples.subpackage.ClassInSubpackage", visitor.getImports().get(2).getName());
        assertFalse(visitor.getImports().get(2).isPrimitive());
    }

    @Test
    public void testStaticImports() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "extractortest/ClassA.java");
        unit.accept(visitor, null);

        assertEquals(3, visitor.getImports().size());

        assertEquals(
                "examples.extractortest.namespace.ClassB", visitor.getImports().get(0).getName());
        assertEquals(
                "examples.extractortest.namespace.ClassC", visitor.getImports().get(1).getName());
        assertEquals("java.util.Arrays", visitor.getImports().get(2).getName());
    }
}
