package org.archcnl.owlify.famix.parser;

import static org.junit.Assert.assertNotNull;

import com.github.javaparser.ast.CompilationUnit;
import java.io.FileNotFoundException;
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;
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
        path = "./src/test/java/examples/SimpleClass.java";
        pathToNonJavaFile = "./src/test/java/examples/test.xml";
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
