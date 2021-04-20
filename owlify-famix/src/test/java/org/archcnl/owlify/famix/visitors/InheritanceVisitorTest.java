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

public class InheritanceVisitorTest {

    private InheritanceVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new InheritanceVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testEnumeration() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "Enumeration.java");
        unit.accept(visitor, null);

        assertTrue(visitor.getSupertypes().isEmpty());
    }

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EmptyClass.java");
        unit.accept(visitor, null);

        assertTrue(visitor.getSupertypes().isEmpty());
    }

    @Test
    public void testComplexClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "ComplexClass.java");
        unit.accept(visitor, null);

        assertEquals(2, visitor.getSupertypes().size());

        //        assertEquals("examples.EmptyClass", visitor.getSupertypes().get(0).getName()); //
        // TODO
        //        assertEquals("examples.Interface", visitor.getSupertypes().get(1).getName()); //
        // TODO
        assertFalse(visitor.getSupertypes().get(0).isPrimitive());
        assertFalse(visitor.getSupertypes().get(1).isPrimitive());
    }
}
