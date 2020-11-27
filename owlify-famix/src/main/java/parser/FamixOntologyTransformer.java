package parser;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.jena.ontology.Individual;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import core.AbstractOwlifyComponent;
import core.GeneralSoftwareArtifactOntology;
import exceptions.FileIsNotAJavaClassException;
import ontology.FamixOntology;
import visitors.ImportDeclarationVisitor;
import visitors.InheritanceVisitor;
import visitors.JavaFieldVisitor;
import visitors.JavaTypeVisitor;
import visitors.MarkerAnnotationExpressionVisitor;
import visitors.MethodDeclarationVisitor;
import visitors.NamespaceVisitor;
import visitors.NormalAnnotationExpressionVisitor;
import visitors.SingleMemberAnnotationExpressionVisitor;

public class FamixOntologyTransformer extends AbstractOwlifyComponent {

	private JavaTypeVisitor visitor;
	private JavaParserDelegator delegator;
	private FamixOntology ontology;
	private Map<CompilationUnit, Individual> unitToIndividualMap;
	private Map<Individual, String> individualToNameMap;
	private GeneralSoftwareArtifactOntology mainOntology;

	/**
	 * @param resultPath - Path to the file in which the results will be stored.
	 */
	public FamixOntologyTransformer(String resultPath) {
		super(resultPath);
		InputStream famixOntologyInputStream = getClass().getResourceAsStream("/ontologies/famix.owl");
		ontology = new FamixOntology(famixOntologyInputStream);
		InputStream mainOntologyInputStream = getClass().getResourceAsStream("/ontologies/main.owl");
		mainOntology = new GeneralSoftwareArtifactOntology(mainOntologyInputStream);
		visitor = new JavaTypeVisitor(ontology);
		delegator = new JavaParserDelegator();

		unitToIndividualMap = new HashMap<CompilationUnit, Individual>();
		individualToNameMap = new HashMap<Individual, String>();

	}

	public void transform() {
		CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
		combinedTypeSolver.add(new ReflectionTypeSolver());
		List<String> sourcePaths = super.getSourcePaths();
		for (String path : sourcePaths) {
			System.out.println(path);
			combinedTypeSolver.add(new JavaParserTypeSolver(path));
		}

		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

		resolveAllTypes(sourcePaths);
		parseOtherElements(sourcePaths);
		
		ontology.add(mainOntology.getOntology());
		ontology.save(super.getResultPath());

	}

	private void parseOtherElements(List<String> sourcePaths) {
		for (String path : sourcePaths) {
			for (File file : FileUtils.listFiles(new File(path),
					new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
					TrueFileFilter.INSTANCE)) {
				CompilationUnit unit;
				try {
					unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
					Individual currentUnitIndividual = unitToIndividualMap.get(unit);
					if (currentUnitIndividual != null) {
						// some calls are commented to increase performance
						// however, this decreases the number of architecture
						// violations that can be found
						unit.accept(new InheritanceVisitor(ontology, currentUnitIndividual), null);
						unit.accept(new JavaFieldVisitor(ontology, currentUnitIndividual), null);
//				unit.accept(new ConstructorDeclarationVisitor(ontology, currentUnitIndividual), null);
						unit.accept(new MethodDeclarationVisitor(ontology, currentUnitIndividual), null);
						unit.accept(new NamespaceVisitor(ontology, currentUnitIndividual,
								individualToNameMap.get(currentUnitIndividual)), null);
						unit.accept(new NormalAnnotationExpressionVisitor(ontology, currentUnitIndividual), null);
						unit.accept(new MarkerAnnotationExpressionVisitor(ontology, currentUnitIndividual), null);
						unit.accept(new SingleMemberAnnotationExpressionVisitor(ontology, currentUnitIndividual), null);
//				unit.accept(new AccessVisitor(ontology,currentUnitIndividual), null);
						unit.accept(new ImportDeclarationVisitor(ontology, currentUnitIndividual), null);
					} else { // type is a package-info.java
						System.out.println(file.getAbsolutePath());
					}
				} catch (FileIsNotAJavaClassException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void resolveAllTypes(List<String> sourcePaths) {
		for (String path : sourcePaths) {
			for (File file : FileUtils.listFiles(new File(path),
					new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
					TrueFileFilter.INSTANCE)) {
				CompilationUnit unit;
				try {

					unit = delegator.getCompilationUnitFromFilePath(file.getAbsolutePath());
					unit.accept(visitor, null);
					if (visitor.getFamixTypeIndividual() != null) {
						unitToIndividualMap.put(unit, visitor.getFamixTypeIndividual());
						Individual softwareArtifactFileIndividual = mainOntology.getSoftwareArtifactFileIndividual();
						mainOntology.setHasFilePath(softwareArtifactFileIndividual, file.getAbsolutePath());
						mainOntology.setSoftwareArtifactFileContainsSoftwareArtifact(softwareArtifactFileIndividual,
								visitor.getFamixTypeIndividual());
						individualToNameMap.put(visitor.getFamixTypeIndividual(), visitor.getNameOfFamixType());
					} else {
						System.out.println(file.getAbsolutePath());
					}

				} catch (FileIsNotAJavaClassException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Map<String, String> getProvidedNamespaces() {
		HashMap<String, String> res = new HashMap<>();
		res.put("famix", ontology.getOntologyNamespace());
		return res;
	}

}
