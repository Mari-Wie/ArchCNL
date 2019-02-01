import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.ClassVisitor;
import visitors.FieldVisitor;
import visitors.MarkerAnnotationExpressionVisitor;
import visitors.NormalAnnotationExpressionVisitor;
import visitors.SingleMemberAnnotationExpressionVisitor;

public class AnnotationExpressionVisitorTest {

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";

	private MarkerAnnotationExpressionVisitor markerVisitor;
	private NormalAnnotationExpressionVisitor normalVisitor;
	private SingleMemberAnnotationExpressionVisitor singleVisitor;

	private JavaParserDelegator delegator;

	private CompilationUnit unit;

	private FamixOntology ontology;

	@Before
	public void initializeVisitor() {
		ontology = new FamixOntology("./ontology/famix.owl");

//		normalVisitor = new NormalAnnotationExpressionVisitor(ontology);
//		singleVisitor = new SingleMemberAnnotationExpressionVisitor(ontology);
		delegator = new JavaParserDelegator();
	}

	@Test
	public void testMarkerAnnotationExpression() throws FileIsNotAJavaClassException {
		ClassVisitor visitor = new ClassVisitor(ontology);
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		unit.accept(visitor, null);
		markerVisitor = new MarkerAnnotationExpressionVisitor(ontology, visitor.getFamixTypeIndividual());
		unit.accept(markerVisitor, null);
		
	}

}
