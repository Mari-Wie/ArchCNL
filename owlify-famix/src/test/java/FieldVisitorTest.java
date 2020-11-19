import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.JavaTypeVisitor;
import visitors.JavaFieldVisitor;

public class FieldVisitorTest {

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";
	
	private JavaFieldVisitor visitor;

	private JavaParserDelegator delegator;

	private CompilationUnit unit;
	
	@Before
	public void initializeVisitor() {
		delegator = new JavaParserDelegator();
	}
	
	@Test
	public void testVisitsField() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		FamixOntology ontology = new FamixOntology(famixOntologyInputStream);
		JavaTypeVisitor classVisitor = new JavaTypeVisitor(ontology);
		visitor = new JavaFieldVisitor(ontology, classVisitor.getFamixTypeIndividual());
		unit.accept(visitor, null);
	}

}
