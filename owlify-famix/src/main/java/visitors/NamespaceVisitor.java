package visitors;

import org.apache.jena.ontology.Individual;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ontology.FamixOntology;

public class NamespaceVisitor extends VoidVisitorAdapter<Void> {

	private FamixOntology ontology;
	private Individual currentUnitIndividual;

	public NamespaceVisitor(FamixOntology ontology, Individual currentUnitIndividual) {
		this.ontology = ontology;
		this.currentUnitIndividual = currentUnitIndividual;
		
		Individual metaNamespace = ontology.getNamespaceIndividualWithName("");
		ontology.setHasNamePropertyForNamedEntity("", metaNamespace);
	}

	@Override
	public void visit(PackageDeclaration n, Void arg) {
		createPackageBasedOnQualifiedName(n.getName().asString());
	}

	private void createPackageBasedOnQualifiedName(String packageName) {
		String[] split = packageName.split("\\.");
		String name = "";

		createOrUpdate("", split[0]);

		String parent = split[0];
		;
		for (int n = 1; n < split.length; n++) {
			name = parent + "." + split[n];
			createOrUpdate(parent, name);
			parent = name;
		}
	}

	private void createOrUpdate(String parent, String name) {
		Individual parentNamespace = ontology.getNamespaceIndividualWithName(parent);
		Individual namespaceIndividual = ontology.getNamespaceIndividualWithName(name);

		ontology.setHasNamePropertyForNamedEntity(name, namespaceIndividual);
		ontology.setNamespaceContainsProperty(parentNamespace, namespaceIndividual);
		
		ontology.setNamespaceContainsProperty(namespaceIndividual, currentUnitIndividual);

	}

}
