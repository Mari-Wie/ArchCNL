package org.archcnl.javaparser.parser;

import static org.junit.Assert.assertNotNull;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.javaparser.exceptions.FileIsNotAJavaClassException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CompilationUnitFactoryTest {

    private String path;
    private String pathToNonJavaFile;

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        path = "./src/test/resources/examples/SimpleClass.java";
        pathToNonJavaFile = "./src/test/resources/examples/test.xml";
    }

    @Test
    public void testDelegatorReturnCompilationUnitThatIsNotNull()
            throws FileIsNotAJavaClassException, FileNotFoundException {

        CompilationUnit unit = CompilationUnitFactory.getFromPath(path);
        assertNotNull(unit);
    }

    @Test
    public void testDelegatorThrowsExceptionForNonJavaFiles()
            throws FileIsNotAJavaClassException, FileNotFoundException {
        thrown.expect(FileIsNotAJavaClassException.class);
        CompilationUnitFactory.getFromPath(pathToNonJavaFile);
    }
}
