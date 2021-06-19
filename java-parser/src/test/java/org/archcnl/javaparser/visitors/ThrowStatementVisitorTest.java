package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Test;

public class ThrowStatementVisitorTest extends GenericVisitorTest<ThrowStatementVisitor> {

    private String pathToExampleClassWithExceptions =
            GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                    + "ExampleClassWithExceptions.java";

    @Test
    public void testFullyQualifiedNames()
            throws FileIsNotAJavaClassException, FileNotFoundException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExampleClassWithExceptions);

        unit.accept(visitor, null);

        final List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getName)
                        .collect(Collectors.toList());

        assertEquals(2, exceptions.size());
        assertTrue(exceptions.contains("java.lang.IllegalArgumentException"));
        assertTrue(exceptions.contains("java.lang.IllegalStateException"));
    }

    @Test
    public void testSimpleNames() throws FileIsNotAJavaClassException, FileNotFoundException {
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExampleClassWithExceptions);

        unit.accept(visitor, null);

        final List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getSimpleName)
                        .collect(Collectors.toList());

        assertEquals(2, exceptions.size());
        assertTrue(exceptions.contains("IllegalArgumentException"));
        assertTrue(exceptions.contains("IllegalStateException"));
    }

    @Override
    protected Class<ThrowStatementVisitor> getVisitorClass() {
        return ThrowStatementVisitor.class;
    }
}
