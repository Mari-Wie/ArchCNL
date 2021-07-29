package org.archcnl.javaparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.archcnl.javaparser.visitors.GenericVisitorTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CompilationUnitFactoryTest {

    private final String pathToExampleJavaClass =
            GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + GenericVisitorTest.SIMPLE_CLASS;
    private final String pathToExampleNonJavaClass =
            GenericVisitorTest.PATH_TO_PACKAGE_WITH_TEST_EXAMPLES + "test.xml";

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenValidJavaClass_whenConvertToCompilationUnit_thenReturnValidCompilationUnit()
            throws FileIsNotAJavaClassException, FileNotFoundException {
        // given, when
        final CompilationUnit unit = CompilationUnitFactory.getFromPath(pathToExampleJavaClass);
        // then
        Assert.assertNotNull(unit);
    }

    @Test
    public void
            givenInvalidNonJavaClass_whenConvertToCompilationUnit_thenFileIsNotAJavaClassExceptionThrown()
                    throws FileIsNotAJavaClassException, FileNotFoundException {
        // given
        thrown.expect(FileIsNotAJavaClassException.class);
        // when, then
        CompilationUnitFactory.getFromPath(pathToExampleNonJavaClass);
    }
}
