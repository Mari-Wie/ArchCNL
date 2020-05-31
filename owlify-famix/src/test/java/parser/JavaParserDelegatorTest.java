package parser;

import static org.junit.Assert.assertNotNull;

//import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;

public class JavaParserDelegatorTest {

	private JavaParserDelegator delegator;
	private String path;
	private String pathToNonJavaFile;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void initialize() {
		delegator = new JavaParserDelegator();
		path = "./src/test/java/examples/TestClassA.java";
		pathToNonJavaFile = "./src/test/java/examples/test.xml";
	}

	@Test
	public void testDelegatorReturnCompilationUnitThatIsNotNull() throws FileIsNotAJavaClassException {

		CompilationUnit unit = delegator.getCompilationUnitFromFilePath(path);
		assertNotNull(unit);

	}

	@Test
	public void testDelegatorThrowsExceptionForNonJavaFiles() throws FileIsNotAJavaClassException {
		thrown.expect(FileIsNotAJavaClassException.class);
		delegator.getCompilationUnitFromFilePath(pathToNonJavaFile);
	}

}
