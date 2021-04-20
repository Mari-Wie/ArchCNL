package org.archcnl.owlify.famix.visitors;

import static org.junit.Assert.*;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.FileNotFoundException;
import org.archcnl.owlify.famix.codemodel.Namespace;
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;
import org.archcnl.owlify.famix.parser.CompilationUnitFactory;
import org.junit.Before;
import org.junit.Test;

public class NamespaceVisitorTest {

    private NamespaceVisitor visitor;
    private String pathToExamplePackage = "./src/test/java/examples/";

    @Before
    public void initializeVisitor() {
        visitor = new NamespaceVisitor();

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testEmptyClass() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExamplePackage + "EmptyClass.java");
        unit.accept(visitor, null);

        Namespace namespace = visitor.getNamespace();

        assertEquals("examples", namespace.getName());
        assertEquals(Namespace.TOP, namespace.getParent());
    }

    @Test
    public void testClassInSubpackage() throws FileNotFoundException, FileIsNotAJavaClassException {
        CompilationUnit unit =
                CompilationUnitFactory.getFromPath(
                        pathToExamplePackage + "subpackage/ClassInSubpackage.java");
        unit.accept(visitor, null);

        Namespace namespace = visitor.getNamespace();

        assertEquals("examples.subpackage", namespace.getName());
        assertEquals("examples", namespace.getParent().getName());
        assertEquals(Namespace.TOP, namespace.getParent().getParent());
    }
}
