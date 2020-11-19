import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.TypeParameterVisitor;

public class TypeParameterVisitorTest {
	
	private TypeParameterVisitor typeParameterVisitor;
	private JavaParserDelegator delegator;
	private CompilationUnit unit;
	private String pathToJavaClassWithTypeParameter = "./src/test/java/examples/TestClassWithParameter.java";
	
	@Before
	public void intializeVisitor() {
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		typeParameterVisitor = new TypeParameterVisitor(new FamixOntology(famixOntologyInputStream));
		delegator = new JavaParserDelegator();
	}

	@Test
	public void test() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClassWithTypeParameter);
		unit.accept(typeParameterVisitor, null);
	}

}
