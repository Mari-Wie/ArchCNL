import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.ClassVisitor;
import visitors.FieldVisitor;

public class FieldVisitorTest {

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";
	
	private FieldVisitor visitor;

	private JavaParserDelegator delegator;

	private CompilationUnit unit;
	
	@Before
	public void initializeVisitor() {
		delegator = new JavaParserDelegator();
	}
	
	@Test
	public void testVisitsField() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		FamixOntology ontology = new FamixOntology("./ontology/famix.owl");
		ClassVisitor classVisitor = new ClassVisitor(ontology);
		visitor = new FieldVisitor(ontology, classVisitor.getFamixTypeIndividual());
		unit.accept(visitor, null);
	}

}
