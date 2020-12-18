import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
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
import visitors.JavaFieldVisitor;
import visitors.JavaTypeVisitor;

public class FieldVisitorTest {
	
	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";
	
	private JavaFieldVisitor visitor;

	private JavaParserDelegator delegator;

	private CompilationUnit unit;
	
	@Before
	public void initializeVisitor() {
		delegator = new JavaParserDelegator();
		
		// set a symbol solver
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver());
		combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
	}
	
	@Test
	public void testVisitsField() throws FileIsNotAJavaClassException, FileNotFoundException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		FamixOntology ontology = new FamixOntology(famixOntologyInputStream);
		JavaTypeVisitor classVisitor = new JavaTypeVisitor(ontology);
		unit.accept(classVisitor, null);
		Individual currentUnitIndividual = classVisitor.getFamixTypeIndividual();
		visitor = new JavaFieldVisitor(ontology, currentUnitIndividual);
		unit.accept(visitor, null);
		
		// the visitor's output is stored in the ontology, but as of 2020-12-18
		// there is no way to query it directly. Workaround: store the ontology in a
		// file, read it, and query the results.
		final String RESULT_PATH = "./src/test/resources/result.owl";
		ontology.save(RESULT_PATH);
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
		model.read(new FileInputStream(RESULT_PATH), null);
		
		FamixOntClassesAndProperties a = new FamixOntClassesAndProperties();
		
		Individual attribute0 = a.getAttributeIndividual(model, 0);
		Individual attribute1 = a.getAttributeIndividual(model, 1);
		
		String name0 = (String) attribute0.getProperty(a.getHasNameProperty(model)).asTriple().getObject().getLiteralValue();
		String name1 = (String) attribute1.getProperty(a.getHasNameProperty(model)).asTriple().getObject().getLiteralValue();
		
		assertEquals("tEnum", name0);
		assertEquals("a", name1);
		
	}

}
