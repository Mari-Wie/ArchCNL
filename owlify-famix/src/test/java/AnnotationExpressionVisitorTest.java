import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.JavaTypeVisitor;
//import visitors.JavaFieldVisitor;
import visitors.MarkerAnnotationExpressionVisitor;
//import visitors.NormalAnnotationExpressionVisitor;
//import visitors.SingleMemberAnnotationExpressionVisitor;

public class AnnotationExpressionVisitorTest {

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";

	private MarkerAnnotationExpressionVisitor markerVisitor;
	//private NormalAnnotationExpressionVisitor normalVisitor;
	//private SingleMemberAnnotationExpressionVisitor singleVisitor;

	private JavaParserDelegator delegator;

	private CompilationUnit unit;

	private FamixOntology ontology;

	@Before
	public void initializeVisitor() {
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		ontology = new FamixOntology(famixOntologyInputStream);

//		normalVisitor = new NormalAnnotationExpressionVisitor(ontology);
//		singleVisitor = new SingleMemberAnnotationExpressionVisitor(ontology);
		delegator = new JavaParserDelegator();
	}

	@Test
	public void testMarkerAnnotationExpression() throws FileIsNotAJavaClassException {
		JavaTypeVisitor visitor = new JavaTypeVisitor(ontology);
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		unit.accept(visitor, null);
		markerVisitor = new MarkerAnnotationExpressionVisitor(ontology, visitor.getFamixTypeIndividual());
		unit.accept(markerVisitor, null);
		
	}

}
