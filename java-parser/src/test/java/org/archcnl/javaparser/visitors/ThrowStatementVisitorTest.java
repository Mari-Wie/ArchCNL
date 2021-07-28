package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.parser.CompilationUnitFactory;
import org.archcnl.owlify.famix.codemodel.Type;
import org.junit.Assert;
import org.junit.Test;

public class ThrowStatementVisitorTest extends GenericVisitorTest<ThrowStatementVisitor> {

    private String pathToExampleClassWithExceptions =
            GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES
                    + "ExampleClassWithExceptions.java";

    @Test
    public void givenClassWithExceptions_whenNThrowStatementVisitorVisit_thenThrowsFound()
            throws FileIsNotAJavaClassException, FileNotFoundException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExampleClassWithExceptions);
        // when
        unit.accept(visitor, null);
        // then
        final List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getName)
                        .collect(Collectors.toList());
        Assert.assertEquals(2, exceptions.size());
        Assert.assertTrue(exceptions.contains("java.lang.IllegalArgumentException"));
        Assert.assertTrue(exceptions.contains("java.lang.IllegalStateException"));
    }

    @Test
    public void
            givenClassWithExceptions_whenNThrowStatementVisitorVisit_thenThrowsAsSimpleNamesFound()
                    throws FileIsNotAJavaClassException, FileNotFoundException {
        // given
        final CompilationUnit unit =
                CompilationUnitFactory.getFromPath(pathToExampleClassWithExceptions);
        // when
        unit.accept(visitor, null);
        // then
        final List<String> exceptions =
                visitor.getThrownExceptionType().stream()
                        .map(Type::getSimpleName)
                        .collect(Collectors.toList());
        Assert.assertEquals(2, exceptions.size());
        Assert.assertTrue(exceptions.contains("IllegalArgumentException"));
        Assert.assertTrue(exceptions.contains("IllegalStateException"));
    }

    @Override
    protected Class<ThrowStatementVisitor> getVisitorClass() {
        return ThrowStatementVisitor.class;
    }
}
