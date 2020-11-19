import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

//import org.apache.jena.ontology.Individual;
//import org.apache.jena.rdf.model.Property;
import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import exceptions.FileIsNotAJavaClassException;
//import ontology.FamixOntClassesAndProperties;
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
