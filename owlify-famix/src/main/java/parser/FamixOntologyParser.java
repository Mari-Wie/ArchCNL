package parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;

import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import visitors.ClassVisitor;
import visitors.ConstructorDeclarationVisitor;
import visitors.FieldVisitor;
import visitors.InheritanceVisitor;
import visitors.MarkerAnnotationExpressionVisitor;
import visitors.MethodDeclarationVisitor;

public class FamixOntologyParser {

	private ClassVisitor visitor;
	private JavaParserDelegator delegator;
	private FamixOntology ontology;
	private Map<CompilationUnit, Individual> unitToIndividualMap;

	public FamixOntologyParser() {
		ontology = new FamixOntology("./ontology/famix.owl");
		visitor = new ClassVisitor(ontology);
		delegator = new JavaParserDelegator();

		unitToIndividualMap = new HashMap<CompilationUnit, Individual>();

	}

	public void parseProject(String pathToProject) throws FileIsNotAJavaClassException {

		// 1. step: resolve all types
		for (File file : FileUtils.listFiles(new File(pathToProject),
				new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
				TrueFileFilter.INSTANCE)) {
			CompilationUnit unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
			unit.accept(visitor, null);
			unitToIndividualMap.put(unit, visitor.getFamixTypeIndividual());
		}

		// 2. step parse the other elements
		for (File file : FileUtils.listFiles(new File(pathToProject),
				new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
				TrueFileFilter.INSTANCE)) {
			CompilationUnit unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
			Individual currentUnitIndividual = unitToIndividualMap.get(unit);
			unit.accept(new InheritanceVisitor(ontology, currentUnitIndividual), null);
			unit.accept(new FieldVisitor(ontology, currentUnitIndividual), null);
			unit.accept(new ConstructorDeclarationVisitor(ontology, currentUnitIndividual), null);
			unit.accept(new MethodDeclarationVisitor(ontology, currentUnitIndividual), null);
		}

		ontology.save("./result.owl");

	}

	public static void main(String[] args) throws FileIsNotAJavaClassException {
		FamixOntologyParser parser = new FamixOntologyParser();
		parser.parseProject("C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl_test\\TestProject");
	}

}
