import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.jena.graph.Node;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntClassesAndProperties;
import ontology.FamixOntology;
import parser.JavaParserDelegator;
import visitors.JavaTypeVisitor;
import visitors.MarkerAnnotationExpressionVisitor;

public class AnnotationExpressionVisitorTest {

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";

	private MarkerAnnotationExpressionVisitor markerVisitor;
	
	private JavaParserDelegator delegator;

	private CompilationUnit unit;

	private FamixOntology ontology;

	@Before
	public void initializeVisitor() {
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		ontology = new FamixOntology(famixOntologyInputStream);

		delegator = new JavaParserDelegator();
		
		// set a symbol solver
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver());
		combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
	}

	@Test
	public void testMarkerAnnotationExpression() throws FileIsNotAJavaClassException, FileNotFoundException {
		JavaTypeVisitor visitor = new JavaTypeVisitor(ontology);
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		unit.accept(visitor, null);
		markerVisitor = new MarkerAnnotationExpressionVisitor(ontology, visitor.getFamixTypeIndividual());
		unit.accept(markerVisitor, null);
		
		// the visitor's output is stored in the ontology, but as of 2020-12-18
		// there is no way to query it directly. Workaround: store the ontology in a
		// file, read it, and query the results.
		final String RESULT_PATH = "./src/test/resources/result.owl";
		ontology.save(RESULT_PATH);
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(new FileInputStream(RESULT_PATH), null);
		
		FamixOntClassesAndProperties a = new FamixOntClassesAndProperties();
		
		Property hasAnnotationType = a.getHasAnnotationTypeProperty(model);
		Property hasAnnotation = a.getHasAnnotationInstanceProperty(model);
		Property hasName = a.getHasNameProperty(model);
		
		Individual annotationType = a.getAnnotationTypeIndividual(model, "Deprecated", 1);
		Individual annotationInstance = a.getAnnotationInstanceIndividual(model, 0);
		Individual clazz = a.getFamixClassIndividual(model, "examples.TestClassA", 0);
		
		assertEquals(annotationInstance, clazz.getProperty(hasAnnotation).getResource());
		assertEquals(annotationType, annotationInstance.getProperty(hasAnnotationType).getResource());
		assertEquals("Deprecated", annotationType.getProperty(hasName).getString());
	}

}
