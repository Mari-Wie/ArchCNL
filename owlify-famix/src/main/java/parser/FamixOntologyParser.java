package parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.CompilationUnit;

import core.OwlifyComponent;
import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import visitors.JavaTypeVisitor;
import visitors.ConstructorDeclarationVisitor;
import visitors.JavaFieldVisitor;
import visitors.InheritanceVisitor;
import visitors.MethodDeclarationVisitor;
import visitors.NamespaceVisitor;

public class FamixOntologyParser implements OwlifyComponent {

	private JavaTypeVisitor visitor;
	private JavaParserDelegator delegator;
	private FamixOntology ontology;
	private Map<CompilationUnit, Individual> unitToIndividualMap;

	private String sourcePath;

	public FamixOntologyParser() {
		ontology = new FamixOntology("./ontology/famix.owl");
		visitor = new JavaTypeVisitor(ontology);
		delegator = new JavaParserDelegator();

		unitToIndividualMap = new HashMap<CompilationUnit, Individual>();

	}

	public static void main(String[] args) throws FileIsNotAJavaClassException {
		FamixOntologyParser parser = new FamixOntologyParser();
		parser.setSource("C:\\Users\\sandr\\Documents\\workspaces\\workspace_cnl_test\\TestProject");
		parser.transform();
	}

	public void setSource(String path) {
		this.sourcePath = path;
	}

	public void transform() {

		// 1. step: resolve all types
		for (File file : FileUtils.listFiles(new File(this.sourcePath),
				new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
				TrueFileFilter.INSTANCE)) {
			CompilationUnit unit;
			try {
				unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
				unit.accept(visitor, null);
				unitToIndividualMap.put(unit, visitor.getFamixTypeIndividual());
			} catch (FileIsNotAJavaClassException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 2. step parse the other elements
		for (File file : FileUtils.listFiles(new File(this.sourcePath),
				new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
				TrueFileFilter.INSTANCE)) {
			CompilationUnit unit;
			try {
				unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
				Individual currentUnitIndividual = unitToIndividualMap.get(unit);
				unit.accept(new InheritanceVisitor(ontology, currentUnitIndividual), null);
				unit.accept(new JavaFieldVisitor(ontology, currentUnitIndividual), null);
				unit.accept(new ConstructorDeclarationVisitor(ontology, currentUnitIndividual), null);
				unit.accept(new MethodDeclarationVisitor(ontology, currentUnitIndividual), null);
				unit.accept(new NamespaceVisitor(ontology, currentUnitIndividual), null);
			} catch (FileIsNotAJavaClassException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ontology.save("./result.owl");

	}

	public String getResultPath() {
		return "./result.owl";
	}

}
