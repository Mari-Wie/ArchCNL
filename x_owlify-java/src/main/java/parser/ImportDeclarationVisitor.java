package parser;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class ImportDeclarationVisitor extends VoidVisitorAdapter<Void> {

	private Individual javaClassIndividual;
	private OntModel ontoModel;
	private String ontologyNamespace;
	private ObjectProperty dependsOnProperty;

	public ImportDeclarationVisitor(String codeOntologyNamespace, OntModel ontoModel, Individual javaClassIndividual) {

		this.ontoModel = ontoModel;
		this.ontologyNamespace = codeOntologyNamespace;
		this.javaClassIndividual = javaClassIndividual;
		this.dependsOnProperty = ontoModel.getObjectProperty(ontologyNamespace + "dependsOn");

	}

	@Override
	public void visit(ImportDeclaration n, Void arg) {

		String[] split = n.getName().toString().split("\\.");
		String type = split[split.length - 1];
		Individual typeIndividual = IndividualCache.getInstance().getTypeIndividual(type);

		if (typeIndividual != null) {
			this.javaClassIndividual.addProperty(dependsOnProperty, typeIndividual);
		} else {
			System.out.println("type: " + type);
		}

	}

}
