package org.archcnl.javaparser.visitors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Before;
import org.junit.Test;

public class ThrowStatementVisitorTest {

    private String pathToJavaClass = "./src/test/java/examples/ExampleClassWithExceptions.java";

    @Before
    public void initializeVisitor() {

        // set a symbol solver
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/"));
        StaticJavaParser.getConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
    }

    @Test
    public void testFullyQualifiedNames()
            throws FileIsNotAJavaClassException, FileNotFoundException {
        CompilationUnit unit = CompilationUnitFactory.getFromPath(pathToJavaClass);

        ThrowStatementVisitor visitor = new ThrowStatementVisitor();
        unit.accept(visitor, null);

        List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getName)
                        .collect(Collectors.toList());

        assertEquals(2, exceptions.size());
        assertTrue(exceptions.contains("java.lang.IllegalArgumentException"));
        assertTrue(exceptions.contains("java.lang.IllegalStateException"));
    }

    @Test
    public void testSimpleNames() throws FileIsNotAJavaClassException, FileNotFoundException {
        CompilationUnit unit = CompilationUnitFactory.getFromPath(pathToJavaClass);

        ThrowStatementVisitor visitor = new ThrowStatementVisitor();
        unit.accept(visitor, null);

        List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getSimpleName)
                        .collect(Collectors.toList());

        assertEquals(2, exceptions.size());
        assertTrue(exceptions.contains("IllegalArgumentException"));
        assertTrue(exceptions.contains("IllegalStateException"));
    }
}
