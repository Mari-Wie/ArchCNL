import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import exceptions.FileIsNotAJavaClassException;
import parser.JavaParserDelegator;
import visitors.JavaTypeVisitor;

public class ClassVistorTest {
	
	private JavaTypeVisitor classVisitor;
	private JavaParserDelegator delegator;
	private CompilationUnit unit;

	private String pathToJavaClass = "./src/test/java/examples/TestClassA.java";
	private String pathToJavaInterface = "./src/test/java/examples/Interface.java";
	private String pathToEnum = "./src/test/java/examples/TestEnum.java";
	private String pathToAnnotation = "./src/test/java/examples/TestAnnotation.java";

	@Before
	public void intializeVisitor() {
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		classVisitor = new JavaTypeVisitor(famixOntologyInputStream);
		delegator = new JavaParserDelegator();
		
		// set a symbol solver
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver());
		combinedTypeSolver.add(new JavaParserTypeSolver("./src/test/java/examples/"));
		StaticJavaParser.getConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
	}

	@Test
	public void testParserCreatesFamixClassIndividualForJavaClass() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		unit.accept(classVisitor, null);
		assertNotNull(classVisitor.getFamixTypeIndividual());
		assertEquals("FamixClass", classVisitor.getFamixTypeIndividual().getOntClass().getLocalName());
	}
	
	@Test
	public void testParserCreatesInterfaceIndividualForInterface() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaInterface);
		unit.accept(classVisitor, null);
		assertNotNull(classVisitor.getFamixTypeIndividual());
		assertEquals("FamixClass", classVisitor.getFamixTypeIndividual().getOntClass().getLocalName());
	}

	@Test
	public void testParserCreatesAnnotationIndividualForAnnotationDeclaration() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToAnnotation);
		unit.accept(classVisitor, null);
		assertNotNull(classVisitor.getFamixTypeIndividual());
		assertEquals("AnnotationType", classVisitor.getFamixTypeIndividual().getOntClass().getLocalName());
	}

	@Test
	public void testParserCreatesEnumIndividualForEnumDeclaration() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToEnum);
		unit.accept(classVisitor, null);
		assertNotNull(classVisitor.getFamixTypeIndividual());
		assertEquals("Enum", classVisitor.getFamixTypeIndividual().getOntClass().getLocalName());
	}
	
	@Test
	public void testParserCreatesDeclaredType() throws FileIsNotAJavaClassException {
		unit = delegator.getCompilationUnitFromFilePath(pathToJavaClass);
		unit.accept(classVisitor, null);
	}
	
}
