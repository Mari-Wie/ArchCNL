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

    private String pathToTryCatchThrowClass =
            GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "TryCatchThrow.java";

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

    /*
     * This test uses a specific class, where an external exception is thrown.
     * An external exception is an exception, that is not inside the path analyzed by the javaparser.
     * The javaparser fails with either a UnsolvedSymbolException or a RuntimeException,
     *  depending on the type of usage.
     * This test is to verify, that this error is logged but does not stop the execution.
     * That is why it is expected, that no exceptions are found.
     */
    @Test
    public void testTryCatchThrow() throws FileIsNotAJavaClassException, FileNotFoundException {
        final CompilationUnit unit = CompilationUnitFactory.getFromPath(pathToTryCatchThrowClass);

        unit.accept(visitor, null);

        final List<Type> exceptions = visitor.getThrownExceptionType();

        assertEquals(0, exceptions.size());
    }

    @Override
    protected Class<ThrowStatementVisitor> getVisitorClass() {
        return ThrowStatementVisitor.class;
    }
}
